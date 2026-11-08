package com.mrthaw.inventory_backend.goods_received.service;

import com.mrthaw.inventory_backend.common.dto.CommonItemListRequestDto;
import com.mrthaw.inventory_backend.common.enum_constant.FormStatus;
import com.mrthaw.inventory_backend.common.enum_constant.FormType;
import com.mrthaw.inventory_backend.common.exception.DataNotFoundException;
import com.mrthaw.inventory_backend.common.exception.InvalidFormStatusException;
import com.mrthaw.inventory_backend.common.exception.ItemMismatchException;
import com.mrthaw.inventory_backend.common.exception.ItemQtyExceedException;
import com.mrthaw.inventory_backend.common.service.FormNoSequenceService;
import com.mrthaw.inventory_backend.common.util.CommonUtils;
import com.mrthaw.inventory_backend.common.util.PageResult;
import com.mrthaw.inventory_backend.goods_received.dto.*;
import com.mrthaw.inventory_backend.goods_received.entity.GoodsReceived;
import com.mrthaw.inventory_backend.goods_received.event.GoodsReceivedCreatedEvent;
import com.mrthaw.inventory_backend.goods_received.event.GoodsReceivedEventItem;
import com.mrthaw.inventory_backend.goods_received.jpa_specification.GoodsReceivedSpecification;
import com.mrthaw.inventory_backend.goods_received.repo.GoodsReceivedRepo;
import com.mrthaw.inventory_backend.purchase_order.api.PurchaseOrderApi;
import com.mrthaw.inventory_backend.purchase_order.dto.PurchaseOrderDetailResponseDto;

import com.mrthaw.inventory_backend.purchase_order.dto.PurchaseOrderItemRequestDto;
import jakarta.persistence.LockModeType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class GoodsReceivedServiceImpl implements GoodsReceivedService {

    private final GoodsReceivedRepo goodsReceivedRepo;

    private final FormNoSequenceService formNoSequenceService;

    private final PurchaseOrderApi purchaseOrderApi;

    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public GoodsReceivedDetailResponseDto findById(Long goodsReceivedId) {
        return goodsReceivedRepo.findById(goodsReceivedId)
                .map(GoodsReceivedDetailResponseDto::toDto)
                .orElseThrow(() -> new DataNotFoundException("Goods Received not found"));
    }

    @Override
    public PageResult<GoodsReceivedResponseDto> searchGoodsReceiveds(GoodsReceivedSearchRequestDto searchRequestDto) {
        return PageResult.toPageResult(goodsReceivedRepo.findAll(GoodsReceivedSpecification.createSearchSpecification(searchRequestDto),
                        PageResult.createPageable(searchRequestDto, Sort.by("id").descending())),
                        GoodsReceivedResponseDto::toDto);
    }

    @Override
    public Long createGoodsReceived(GoodsReceivedCreateRequestDto createRequestDto) {

        PurchaseOrderDetailResponseDto purchaseOrderResponseDto = purchaseOrderApi.findById(createRequestDto.getPurchaseOrderId());

        if (! purchaseOrderResponseDto.isValidToProcess())
            throw new InvalidFormStatusException("Current form status is" + FormStatus.getDescByCode(purchaseOrderResponseDto.getFormStatus().getCode()));

        GoodsReceived goodsReceived = GoodsReceived.builder()
                .purchaseOrderId(purchaseOrderResponseDto.getId())
                .goodsReceivedDate(createRequestDto.getGoodsReceivedDate())
                .goodsReceivedNo(formNoSequenceService.generateNextFormNo(FormType.GOODS_RECEIVED.getCode()))
                .formStatus(FormStatus.READY_TO_PROCESS.getCode())
                .build();


        goodsReceivedRepo.save(goodsReceived);

        if(! onlyContainPurchaseOrderItemList(createRequestDto, purchaseOrderResponseDto.getItemList()))
            throw new ItemMismatchException("Goods received item list doesn't match with purchase order item list");

        if(itemQtyExceedThanPurchaseOrderItemQty(createRequestDto.getItemList(), purchaseOrderResponseDto.getItemList()))
            throw new ItemQtyExceedException("Goods received item qty exceed than the purchase order item qty");

        goodsReceived.addAllItems(createRequestDto.getItemList());

        applicationEventPublisher.publishEvent(
                new GoodsReceivedCreatedEvent(
                        goodsReceived.getId(),
                        goodsReceived.getPurchaseOrderId(),
                        goodsReceived.getGoodsReceivedNo(),
                        goodsReceived.getItems().stream()
                                .map(GoodsReceivedEventItem::toEventItem)
                                .toList(),
                        goodsReceived.getCreatedTime())
        );

        return goodsReceived.getId();
    }

    private boolean itemQtyExceedThanPurchaseOrderItemQty(List<GoodsReceivedItemRequestDto> itemList, List<PurchaseOrderItemRequestDto> purchaseOrderItemList) {

        Map<Long, BigDecimal> purchaseOrderItemRemainingQtyMap = purchaseOrderItemList
                .stream()
                .collect(Collectors.toMap(PurchaseOrderItemRequestDto::getItemId, item -> item.getQty().subtract(item.getReceivedQty())));

        for(GoodsReceivedItemRequestDto item : itemList){
            if(item.getQty().compareTo(purchaseOrderItemRemainingQtyMap.get(item.getItemId())) > 0)
                return true;
        }

        return false;
    }

    private boolean onlyContainPurchaseOrderItemList(GoodsReceivedCreateRequestDto createRequestDto, List<PurchaseOrderItemRequestDto> purchaseOrderItemList) {
        Set<Long> purchaseOrderItemIdList = purchaseOrderItemList
                .stream()
                .map(PurchaseOrderItemRequestDto::getItemId)
                .collect(Collectors.toSet());

        for(GoodsReceivedItemRequestDto  item : createRequestDto.getItemList()) {
            if(! purchaseOrderItemIdList.contains(item.getItemId()))
                return false;
        }

        return true;
    }

    @Override
    public void updateGoodsReceived(GoodsReceivedUpdateRequestDto updateRequestDto) {
        GoodsReceived goodsReceived = goodsReceivedRepo.findById(updateRequestDto.getId())
                .orElseThrow(() -> new DataNotFoundException("Goods Received not found"));

        if(! goodsReceived.getFormStatus().equals(FormStatus.DRAFT.getCode()))
            throw new InvalidFormStatusException("Current form status is " + FormStatus.getDescByCode(goodsReceived.getFormStatus()));

        if(updateRequestDto.getGoodsReceivedDate() != null)
            goodsReceived.setGoodsReceivedDate(updateRequestDto.getGoodsReceivedDate());

        if(updateRequestDto.getPurchaseOrderId() != null)
            goodsReceived.setPurchaseOrderId(updateRequestDto.getPurchaseOrderId());

        if(CommonUtils.isValidList(updateRequestDto.getItemList())) {
            goodsReceived.updateAllItems(updateRequestDto.getItemList());
        }

        goodsReceivedRepo.save(goodsReceived);
    }


}

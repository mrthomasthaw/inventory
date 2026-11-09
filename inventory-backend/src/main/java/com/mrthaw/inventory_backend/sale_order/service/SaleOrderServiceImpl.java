package com.mrthaw.inventory_backend.sale_order.service;

import com.mrthaw.inventory_backend.common.enum_constant.FormStatus;
import com.mrthaw.inventory_backend.common.enum_constant.FormType;
import com.mrthaw.inventory_backend.common.exception.DataNotFoundException;
import com.mrthaw.inventory_backend.common.exception.InvalidFormStatusException;
import com.mrthaw.inventory_backend.common.exception.ItemMismatchException;
import com.mrthaw.inventory_backend.common.service.FormNoSequenceService;
import com.mrthaw.inventory_backend.common.util.CommonUtils;
import com.mrthaw.inventory_backend.common.util.PageResult;
import com.mrthaw.inventory_backend.goods_issued.entity.GoodsIssued;
import com.mrthaw.inventory_backend.goods_issued.entity.GoodsIssuedItem;
import com.mrthaw.inventory_backend.goods_issued.event.GoodsIssuedEventItem;
import com.mrthaw.inventory_backend.goods_issued.event.GoodsIssuedSubmitEvent;
import com.mrthaw.inventory_backend.master_data.business_partner.entity.BusinessPartner;
import com.mrthaw.inventory_backend.master_data.item.entity.Item;
import com.mrthaw.inventory_backend.sale_order.dto.*;
import com.mrthaw.inventory_backend.sale_order.entity.SaleOrder;
import com.mrthaw.inventory_backend.sale_order.entity.SaleOrderItem;
import com.mrthaw.inventory_backend.sale_order.jpa_specification.SaleOrderSpecification;
import com.mrthaw.inventory_backend.sale_order.repo.SaleOrderRepo;
import lombok.RequiredArgsConstructor;
import org.hibernate.LockMode;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor
public class SaleOrderServiceImpl implements SaleOrderService {

    private final SaleOrderRepo saleOrderRepo;

    private final FormNoSequenceService formNoSequenceService;

    @Override
    public PageResult<SaleOrderResponseDto> searchSaleOrders(SaleOrderSearchRequestDto searchDto) {
        return PageResult.toPageResult(saleOrderRepo.findAll(SaleOrderSpecification.createSearchSpecification(searchDto),
                PageResult.createPageable(searchDto, Sort.by("id").descending())), SaleOrderResponseDto::toDto);
    }

    @Override
    public Long createSaleOrder(SaleOrderCreateRequestDto createRequestDto, String requestId) {

        SaleOrder saleOrder = SaleOrder.builder()
                .saleOrderNo(formNoSequenceService.generateNextFormNo(FormType.SALE_ORDER.getCode()))
                .saleOrderDate(createRequestDto.getSaleOrderDate())
                .formStatus(FormStatus.READY_TO_PROCESS.getCode())
                .customer(new BusinessPartner(createRequestDto.getCustomerId()))
                .idempotentKey(requestId)
                .build();

        saleOrderRepo.save(saleOrder);

        saleOrder.addAllItems(createRequestDto.getItemList()
                .stream()
                .map(SaleOrderRequestItemDto::toCreateEntity)
                .toList());

        return saleOrder.getId();
    }

    @Override
    public void updateSaleOrder(SaleOrderUpdateRequestDto updateRequestDto) {
        SaleOrder saleOrder = saleOrderRepo.findById(updateRequestDto.getId())
                .orElseThrow(() -> new DataNotFoundException("Sale Order not found"));

        if(saleOrder.getFormStatus() != FormStatus.DRAFT.getCode())
            throw new InvalidFormStatusException("Current form status is " + FormStatus.getDescByCode(saleOrder.getFormStatus()));

        if(updateRequestDto.getCustomerId() != null)
            saleOrder.setCustomer(new BusinessPartner(updateRequestDto.getCustomerId()));

        if(updateRequestDto.getSaleOrderDate() != null)
            saleOrder.setSaleOrderDate(updateRequestDto.getSaleOrderDate());

        if(CommonUtils.isValidList(updateRequestDto.getItemList())) {
            saleOrder.updateAllItems(updateRequestDto.getItemList()
                    .stream()
                    .map(SaleOrderRequestItemDto::toUpdateEntity)
                    .toList());
        }
    }

    @Override
    public SaleOrderResponseDto findById(Long id) {
        return saleOrderRepo.findById(id)
                .map(SaleOrderResponseDto::toDto)
                .orElseThrow(() -> new DataNotFoundException("Sale order not found"));
    }

    @Override
    public SaleOrderResponseDto findBySONo(String formNo, LockMode lockMode) {
        return saleOrderRepo.findBySaleOrderNo(formNo, lockMode)
                .map(SaleOrderResponseDto::toDto)
                .orElseThrow(() -> new DataNotFoundException("Sale order not found"));
    }


    @Override
    public void updateSaleOrderOnGoodsIssuedSubmit(GoodsIssuedSubmitEvent event) {

        SaleOrder saleOrder = saleOrderRepo.findBySaleOrderNo(event.referenceFormNo(), LockMode.PESSIMISTIC_WRITE)
                .orElseThrow(() -> new  DataNotFoundException("Sale Order not found"));

        if(! saleOrder.isValidFormStatusToProcess())
            throw new  InvalidFormStatusException("Invalid form status");

        System.out.println("Go ahead");

        // check if the issued item list ids match with sale order item list

        if(! checkItemMismatchBetweenSaleOrderItemList(event, saleOrder))
            throw new ItemMismatchException("Goods issued item mismatch with sale order item list");

        // check if the issued item list ids match with sale order item list

        //Update issued qty

        Map<Long, BigDecimal> itemQtyMap = event.itemList()
                .stream()
                .collect(Collectors.toMap((GoodsIssuedEventItem::itemId), GoodsIssuedEventItem::qty));

        saleOrder.updateAllItemsIssuedQty(itemQtyMap);
        //Update issued qty

        if(saleOrder.hasRemainingQty())
            saleOrder.setFormStatus(FormStatus.PROCESSING.getCode());
        else
            saleOrder.setFormStatus(FormStatus.COMPLETE.getCode());

        //update sale order item qty (issued qty)
        //update sale order form status
    }

    private boolean checkItemMismatchBetweenSaleOrderItemList(GoodsIssuedSubmitEvent event, SaleOrder saleOrder) {
        List<SaleOrderItem> saleOrderItemList = saleOrder.getAllItems();
        Set<Long> itemIdSet = saleOrderItemList.stream()
                .map(i -> i.getItem().getId())
                .collect(Collectors.toSet());

        for(GoodsIssuedEventItem eventItem : event.itemList()) {
            if(! itemIdSet.contains(eventItem.itemId()))
                return false;
        }

        return true;
    }
}

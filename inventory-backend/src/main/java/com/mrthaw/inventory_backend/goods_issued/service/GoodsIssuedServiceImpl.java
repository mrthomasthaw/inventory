package com.mrthaw.inventory_backend.goods_issued.service;

import com.mrthaw.inventory_backend.common.enum_constant.FormStatus;
import com.mrthaw.inventory_backend.common.enum_constant.FormType;
import com.mrthaw.inventory_backend.common.exception.InvalidFormStatusException;
import com.mrthaw.inventory_backend.common.service.FormNoSequenceService;
import com.mrthaw.inventory_backend.common.util.PageResult;
import com.mrthaw.inventory_backend.goods_issued.event.GoodsIssuedEventItem;
import com.mrthaw.inventory_backend.goods_issued.event.GoodsIssuedSubmitEvent;
import com.mrthaw.inventory_backend.goods_issued.jpa_specification.GoodsIssuedSpecification;
import com.mrthaw.inventory_backend.goods_issued.dto.GoodsIssuedCreateRequestDto;
import com.mrthaw.inventory_backend.goods_issued.dto.GoodsIssuedResponseDto;
import com.mrthaw.inventory_backend.goods_issued.dto.GoodsIssuedSearchRequestDto;
import com.mrthaw.inventory_backend.goods_issued.entity.GoodsIssued;
import com.mrthaw.inventory_backend.goods_issued.entity.GoodsIssuedItem;
import com.mrthaw.inventory_backend.goods_issued.repo.GoodsIssuedRepo;
import com.mrthaw.inventory_backend.master_data.item.entity.Item;
import com.mrthaw.inventory_backend.sale_order.api.SaleOrderApi;
import com.mrthaw.inventory_backend.sale_order.dto.SaleOrderResponseDto;
import lombok.RequiredArgsConstructor;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class GoodsIssuedServiceImpl implements GoodsIssuedService {

    private final GoodsIssuedRepo goodsIssuedRepo;

    private final FormNoSequenceService formNoSequenceService;

    private final SaleOrderApi saleOrderApi;

    private final ApplicationEventPublisher eventPublisher;

    @Override
    public Long createGoodsIssued(GoodsIssuedCreateRequestDto createRequestDto, String requestId) {

        SaleOrderResponseDto saleOrderResponseDto = saleOrderApi.findBySONo(createRequestDto.getReferenceFormNo(), LockMode.PESSIMISTIC_WRITE);

        if(! saleOrderResponseDto.isValidFormStatusToProcess())
            throw new InvalidFormStatusException("Current form status is " + saleOrderResponseDto.getFormStatus().getDesc());

        GoodsIssued goodsIssued = GoodsIssued.builder()
                .goodsIssuedNo(formNoSequenceService.generateNextFormNo(FormType.GOODS_ISSUED.getCode()))
                .goodsIssuedDate(createRequestDto.getGoodsIssuedDate())
                .referenceFormNo(createRequestDto.getReferenceFormNo())
                .idempotentKey(requestId)
                .build();

        goodsIssuedRepo.save(goodsIssued);

        goodsIssued.addAllItem(
                createRequestDto.getItemList()
                        .stream()
                        .map(item -> GoodsIssuedItem.builder()
                                .item(new Item(item.getItemId()))
                                .qty(item.getQty())
                                .totalAmount(item.getQty().multiply(item.getPrice()))
                                .build())
                        .toList()
        );

        eventPublisher.publishEvent(new GoodsIssuedSubmitEvent(
                goodsIssued.getId(),
                goodsIssued.getGoodsIssuedNo(),
                goodsIssued.getGoodsIssuedDate(),
                goodsIssued.getReferenceFormNo(),
                goodsIssued.getAllItems()
                        .stream()
                        .map(item -> new GoodsIssuedEventItem(
                                item.getItem().getId(),
                                item.getQty(),
                                item.getTotalAmount()
                        ))
                        .toList()
        ));


        return goodsIssued.getId();
    }

    @Override
    public PageResult<GoodsIssuedResponseDto> searchGoodsIssueds(GoodsIssuedSearchRequestDto searchDto) {
        return PageResult.toPageResult(goodsIssuedRepo.findAll(GoodsIssuedSpecification.createSearchSpecification(searchDto),
                PageResult.createPageable(searchDto, Sort.by("id").descending())), GoodsIssuedResponseDto::toDto);
    }


}

package com.mrthaw.inventory_backend.purchase_order.service;

import com.mrthaw.inventory_backend.common.enum_constant.FormStatus;
import com.mrthaw.inventory_backend.common.enum_constant.FormType;
import com.mrthaw.inventory_backend.common.exception.DataNotFoundException;
import com.mrthaw.inventory_backend.common.exception.InvalidFormStatusException;
import com.mrthaw.inventory_backend.common.service.FormNoSequenceService;
import com.mrthaw.inventory_backend.common.util.PageResult;

import com.mrthaw.inventory_backend.master_data.business_partner.entity.BusinessPartner;
import com.mrthaw.inventory_backend.purchase_order.dto.*;
import com.mrthaw.inventory_backend.purchase_order.entity.PurchaseOrder;
import com.mrthaw.inventory_backend.purchase_order.jpa_specification.PurchaseOrderSpecification;
import com.mrthaw.inventory_backend.purchase_order.repo.PurchaseOrderRepo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Validated
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

    private final PurchaseOrderRepo purchaseOrderRepo;

    private final FormNoSequenceService formNoSequenceService;

    @Override
    public PageResult<PurchaseOrderResponseDto> searchPurchaseOrders(PurchaseOrderSearchRequestDto searchRequestDto) {
        return PageResult.toPageResult(purchaseOrderRepo.findAll(PurchaseOrderSpecification.createSearchSpecification(searchRequestDto),
                PageResult.createPageable(searchRequestDto, Sort.by("id").descending())), PurchaseOrderResponseDto::toDto);
    }

    @Override
    public String createPurchaseOrder(@Valid PurchaseOrderCreateRequestDto createRequestDto) {

        log.info("{}.....start...createPurchaseOrder....", Thread.currentThread().getName());

        PurchaseOrder purchaseOrder = PurchaseOrder.builder()
                .purchaseOrderNo(formNoSequenceService.generateNextFormNo(FormType.PURCHASE_ORDER.getCode()))
                .purchaseDate(createRequestDto.getPurchaseDate())
                .formStatus(FormStatus.READY_TO_PROCESS.getCode())
                .supplier(new BusinessPartner(createRequestDto.getSupplierId()))
                .build();

        log.info("{}.....generated form no.... {}", Thread.currentThread().getName(), purchaseOrder.getPurchaseOrderNo());
        purchaseOrderRepo.save(purchaseOrder);

        purchaseOrder.addAllItems(createRequestDto.getItemList());

        log.info("{}.....end...createPurchaseOrder....", Thread.currentThread().getName());

        return purchaseOrder.getPurchaseOrderNo();
    }

    @Override
    public void updatePurchaseOrder(PurchaseOrderUpdateRequestDto updateRequestDto) {
        PurchaseOrder purchaseOrder = purchaseOrderRepo.findById(updateRequestDto.getId())
                .orElseThrow(() -> new DataNotFoundException("Purchase order not found"));

        if(! purchaseOrder.getFormStatus().equals(FormStatus.DRAFT.getCode()))
            throw new InvalidFormStatusException("Current form status is " + FormStatus.getDescByCode(purchaseOrder.getFormStatus()));

        if(updateRequestDto.getPurchaseDate() != null) {
            purchaseOrder.setPurchaseDate(updateRequestDto.getPurchaseDate());
        }

        if(updateRequestDto.getSupplierId() != null) {
            purchaseOrder.setSupplier(new BusinessPartner(updateRequestDto.getSupplierId()));
        }

        if(! updateRequestDto.getItemList().isEmpty()) {
            purchaseOrder.updateAllItems(updateRequestDto.getItemList());
        }

        purchaseOrderRepo.save(purchaseOrder);
    }

    @Override
    public PurchaseOrderDetailResponseDto findById(Long purchaseOrderId) {
        return purchaseOrderRepo.findById(purchaseOrderId)
                .map(PurchaseOrderDetailResponseDto::toDto)
                .orElseThrow(() -> new DataNotFoundException("Purchase order not found"));
    }
}

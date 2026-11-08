package com.mrthaw.inventory_backend.purchase_order.entity;

import com.mrthaw.inventory_backend.common.entity.AuditingEntity;
import com.mrthaw.inventory_backend.common.entity.BaseEntity;
import com.mrthaw.inventory_backend.common.enum_constant.FormStatus;
import com.mrthaw.inventory_backend.master_data.business_partner.entity.BusinessPartner;
import com.mrthaw.inventory_backend.master_data.item.entity.Item;
import com.mrthaw.inventory_backend.purchase_order.dto.PurchaseOrderItemRequestDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseOrder extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String purchaseOrderNo;

    private LocalDate purchaseDate;

    private Integer formStatus;

    @ManyToOne(fetch =  FetchType.LAZY)
    private BusinessPartner supplier;

    @DecimalMin(value = "0.01")
    private BigDecimal totalQty;

    @DecimalMin(value = "0.01")
    private BigDecimal totalAmount;

    @Builder.Default // For creating a class through builder, it won't initialize the field, so you add this annotation.
                    // when you ignore this list in builder, you tell the lombok to initialize with default value without initializing null
    @OneToMany(mappedBy = "purchaseOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private List<PurchaseOrderItem> purchaseOrderItems = new ArrayList<>();


    public PurchaseOrder(Long id) {
        this.id = id;
    }

    public void addItem(PurchaseOrderItemRequestDto itemDto) {

        PurchaseOrderItem item = PurchaseOrderItem.builder()
                .item(new Item(itemDto.getItemId()))
                .price(itemDto.getPrice())
                .qty(itemDto.getQty())
                .receivedQty(BigDecimal.ZERO)
                .totalAmount(itemDto.getPrice().multiply(itemDto.getQty()))
                .purchaseOrder(this)
                .build();

        purchaseOrderItems.add(item);

        recalculateTotal();
    }

    public void recalculateTotal() {
        this.totalQty = this.purchaseOrderItems.stream()
                .map(PurchaseOrderItem::getQty)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        this.totalAmount = this.purchaseOrderItems.stream()
                .map(PurchaseOrderItem::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

    }

    public void addAllItems(List<PurchaseOrderItemRequestDto> itemList) {
        itemList.forEach(this::addItem);
    }

    public void updateAllItems(List<PurchaseOrderItemRequestDto> itemList) {
        purchaseOrderItems.clear();

        addAllItems(itemList);
    }

    public List<PurchaseOrderItem> getItems() {
        return Collections.unmodifiableList(purchaseOrderItems);
    }

    public boolean isValidFormStatus(FormStatus formStatusToUpdate) {

        if (formStatusToUpdate.equals(FormStatus.PROCESSING)) {
            return this.formStatus.equals(FormStatus.READY_TO_PROCESS.getCode());
        }

        return false;
    }

    public void updateReceivedItemQtyList(Map<Long, BigDecimal> itemQtyMap) {

        for(PurchaseOrderItem item : purchaseOrderItems) {
            BigDecimal qty = itemQtyMap.get(item.getItem().getId());

            if(qty == null) continue;

            item.addReceivedQty(qty);
        }
    }

    public boolean hasRemainingQty() {
        BigDecimal remainingQty = purchaseOrderItems.stream()
                .map(p -> p.getQty().subtract(p.getReceivedQty()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return remainingQty.compareTo(BigDecimal.ZERO) > 0;
    }

    public boolean isValidToProcess() {
        return this.formStatus.equals(FormStatus.PROCESSING.getCode())
                || this.formStatus.equals(FormStatus.READY_TO_PROCESS.getCode());
    }
}

package com.mrthaw.inventory_backend.purchase_order.entity;

import com.mrthaw.inventory_backend.common.entity.AuditingEntity;
import com.mrthaw.inventory_backend.master_data.business_partner.entity.BusinessPartner;
import com.mrthaw.inventory_backend.master_data.item.entity.Item;
import com.mrthaw.inventory_backend.purchase_order.dto.PurchaseOrderItemCreateRequestDto;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseOrder extends AuditingEntity {

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

    @Version
    private Long version;

    public PurchaseOrder(Long id) {
        this.id = id;
    }

    public void addItem(PurchaseOrderItemCreateRequestDto itemDto) {

        PurchaseOrderItem item = PurchaseOrderItem.builder()
                .item(new Item(itemDto.getItemId()))
                .price(itemDto.getPrice())
                .qty(itemDto.getQty())
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

    public void addAllItems(List<PurchaseOrderItemCreateRequestDto> itemList) {
        itemList.forEach(this::addItem);
    }

    public void updateAllItems(List<PurchaseOrderItemCreateRequestDto> itemList) {
        purchaseOrderItems.clear();

        addAllItems(itemList);
    }
}

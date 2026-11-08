package com.mrthaw.inventory_backend.sale_order.entity;

import com.mrthaw.inventory_backend.common.entity.AuditingEntity;
import com.mrthaw.inventory_backend.common.entity.BaseEntity;
import com.mrthaw.inventory_backend.master_data.business_partner.entity.BusinessPartner;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class SaleOrder extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String saleOrderNo;

    private LocalDate saleOrderDate;

    @ManyToOne
    private BusinessPartner customer;

    private Integer formStatus;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @Builder.Default // This makes the field initialize to default field
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "saleOrder")
    List<SaleOrderItem> saleOrderItems = new ArrayList<>();

    @Column(nullable = false)
    private BigDecimal totalQty;

    @Column(nullable = false)
    private BigDecimal totalAmount;


    public void addAllItems(List<SaleOrderItem> items) {
        items.forEach(this::addItem);

        recalculateTotal();
    }

    public void addItem(SaleOrderItem item) {
        item.setSaleOrder(this);
        this.saleOrderItems.add(item);
    }

    public List<SaleOrderItem> getAllItems() {
        return Collections.unmodifiableList(saleOrderItems);
    }


    public void updateAllItems(List<SaleOrderItem> items) {
        this.saleOrderItems.clear();

        addAllItems(items);
    }

    public void recalculateTotal() {
        this.totalQty = this.saleOrderItems.stream()
                .map(SaleOrderItem::getQty)
                .reduce(BigDecimal.ZERO, BigDecimal::add);


        this.totalAmount = this.saleOrderItems
                .stream()
                .map(SaleOrderItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}

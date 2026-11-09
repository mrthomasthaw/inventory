package com.mrthaw.inventory_backend.sale_order.entity;

import com.mrthaw.inventory_backend.common.entity.AuditingEntity;
import com.mrthaw.inventory_backend.common.entity.BaseEntity;
import com.mrthaw.inventory_backend.common.enum_constant.FormStatus;
import com.mrthaw.inventory_backend.master_data.business_partner.entity.BusinessPartner;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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
                .map(SaleOrderItem::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public boolean isValidFormStatusToProcess() {
        if(this.formStatus == null) return false;

        //Ready to process, processing
        if(this.formStatus.equals(FormStatus.READY_TO_PROCESS.getCode()))
            return true;

        if(this.formStatus.equals(FormStatus.PROCESSING.getCode()))
            return true;

        return false;
    }

    public void updateAllItemsIssuedQty(Map<Long, BigDecimal> itemQtyMap) {
        for(SaleOrderItem item : saleOrderItems) {
            BigDecimal qtyToAdd = itemQtyMap.get(item.getItem().getId());
            item.addIssuedQty(qtyToAdd);
        }
    }

    public boolean hasRemainingQty() {

        for(SaleOrderItem item : saleOrderItems) {
           BigDecimal qty = item.getQty() == null ? BigDecimal.ZERO : item.getQty();
           BigDecimal issuedQty = item.getIssuedQty() == null ? BigDecimal.ZERO : item.getIssuedQty();

           if(qty.subtract(issuedQty).compareTo(BigDecimal.ZERO) <= 0) { // if there is no remaining qty
               return false;
           }
        }
        //remainingQty = qty - issued

        return true;
    }
}

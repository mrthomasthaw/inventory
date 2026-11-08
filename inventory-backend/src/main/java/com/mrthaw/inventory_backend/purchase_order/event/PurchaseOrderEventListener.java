package com.mrthaw.inventory_backend.purchase_order.event;

import com.mrthaw.inventory_backend.common.enum_constant.FormStatus;
import com.mrthaw.inventory_backend.goods_received.event.GoodsReceivedCreatedEvent;
import com.mrthaw.inventory_backend.purchase_order.service.PurchaseOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class PurchaseOrderEventListener {

    private final PurchaseOrderService purchaseOrderService;

    @EventListener
    public void handleGoodsReceivedCreateEvent(GoodsReceivedCreatedEvent event) {
        log.info("PurchaseOrderEventListener handleGoodsReceivedCreateEvent");

        purchaseOrderService.updatePurchaseOrderOnGoodsReceived(event.purchaseOrderId(), event.itemList());
    }
}

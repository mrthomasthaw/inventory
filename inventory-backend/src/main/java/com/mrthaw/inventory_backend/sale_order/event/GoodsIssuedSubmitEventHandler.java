package com.mrthaw.inventory_backend.sale_order.event;

import com.mrthaw.inventory_backend.goods_issued.event.GoodsIssuedSubmitEvent;
import com.mrthaw.inventory_backend.sale_order.dto.SaleOrderResponseDto;
import com.mrthaw.inventory_backend.sale_order.service.SaleOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GoodsIssuedSubmitEventHandler {

    private final SaleOrderService saleOrderService;

    @EventListener
    public void handleGoodsIssuedSubmitEvent(GoodsIssuedSubmitEvent event)  {
        System.out.println("Hello... goods issued submitted");

        saleOrderService.updateSaleOrderOnGoodsIssuedSubmit(event);
    }


}

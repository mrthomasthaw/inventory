package com.mrthaw.inventory_backend;


import com.mrthaw.inventory_backend.common.entity.FormNoSequence;
import com.mrthaw.inventory_backend.common.enum_constant.FormStatus;
import com.mrthaw.inventory_backend.common.enum_constant.FormType;
import com.mrthaw.inventory_backend.common.repo.FormNoSequenceRepo;
import com.mrthaw.inventory_backend.common.service.FormNoSequenceService;
import com.mrthaw.inventory_backend.common.util.PageResult;
import com.mrthaw.inventory_backend.goods_received.dto.GoodsReceivedResponseDto;
import com.mrthaw.inventory_backend.goods_received.dto.GoodsReceivedSearchRequestDto;
import com.mrthaw.inventory_backend.goods_received.entity.GoodsReceived;
import com.mrthaw.inventory_backend.goods_received.repo.GoodsReceivedRepo;
import com.mrthaw.inventory_backend.goods_received.service.GoodsReceivedService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Random;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@Transactional
@SpringBootTest
public class GoodsReceivedIntegrationTest {

    @Autowired
    private GoodsReceivedService goodsReceivedService;

    @Autowired
    private GoodsReceivedRepo goodsReceivedRepo;

    @Autowired
    private FormNoSequenceRepo formNoSequenceRepo;
    @Autowired
    private FormNoSequenceService formNoSequenceService;



    @BeforeEach
    public void setup() {
        goodsReceivedRepo.deleteAll();

        FormNoSequence formNoSequence = FormNoSequence.builder()
                .date(LocalDate.now())
                .prefix("GR")
                .formType(FormType.GOODS_RECEIVED.getCode())
                .lastSequence(0L)
                .build();

        formNoSequenceRepo.save(formNoSequence);

        LocalDate[] dates = {
                LocalDate.now(), LocalDate.now().plusDays(5), LocalDate.now().minusDays(20),
                LocalDate.now().minusDays(6), LocalDate.now().minusDays(10)
        };



        for(int x = 0; x < 50; x++){

            int datLen = dates.length;
            Random random = new Random();
            int index = random.nextInt(datLen);

            System.out.println("Hello ...." + random.nextInt(datLen));

            LocalDate date = dates[index];

            System.out.println("Date>>> " + date);

            GoodsReceived goodsReceived = GoodsReceived.builder()
                    .goodsReceivedNo(formNoSequenceService.generateNextFormNo(FormType.GOODS_RECEIVED.getCode()))
                    .goodsReceivedDate(dates[index])
                    .purchaseOrderId(1L)
                    .formStatus(FormStatus.READY_TO_PROCESS.getCode())
                    .build();

            goodsReceivedRepo.save(goodsReceived);
        }
    }

    @Test
    public void return_rows_on_search() {

        System.out.println(".......Show All...............");
        goodsReceivedRepo.findAll()
                                .stream()
                                .map(GoodsReceivedResponseDto::toDto)
                                .forEach(System.out::println);

        System.out.println(".......Show All...............");

        GoodsReceivedSearchRequestDto searchRequestDto = new GoodsReceivedSearchRequestDto();
        //searchRequestDto.setGoodsReceivedNo("GR-202610300001");
        searchRequestDto.setFromDate(LocalDate.now().minusDays(5));
        //searchRequestDto.setToDate(LocalDate.now());

        PageResult<GoodsReceivedResponseDto> pageResult = goodsReceivedService.searchGoodsReceiveds(searchRequestDto);

        //assertThat(pageResult.getList().size()).isEqualTo(1);

        for(GoodsReceivedResponseDto goodsReceivedResponseDto : pageResult.getList()){
            System.out.println(goodsReceivedResponseDto.getGoodsReceivedNo() + " >>>> " + goodsReceivedResponseDto);
        }
    }


}

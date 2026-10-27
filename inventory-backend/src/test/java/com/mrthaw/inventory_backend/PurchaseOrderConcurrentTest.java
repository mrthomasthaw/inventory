package com.mrthaw.inventory_backend;

import com.mrthaw.inventory_backend.purchase_order.dto.PurchaseOrderCreateRequestDto;
import com.mrthaw.inventory_backend.purchase_order.dto.PurchaseOrderItemCreateRequestDto;
import com.mrthaw.inventory_backend.purchase_order.repo.PurchaseOrderRepo;
import com.mrthaw.inventory_backend.purchase_order.service.PurchaseOrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Slf4j
@SpringBootTest
public class PurchaseOrderConcurrentTest {

    @Autowired
    private PurchaseOrderService purchaseOrderService;


    @Test
    public void duplicate_row_when_saving_purchase_order_concurrently() throws ExecutionException, InterruptedException {

        int threadCounts = 16; // no of thread doesn't mean no of task will carry out
        ExecutorService executorService = Executors.newFixedThreadPool(threadCounts);

        log.info("....concurrent test running...");

        List<Future<String>>  futures = new ArrayList<>();

        int taskCountToExecute = 20; // no of task is actually the task will be carried out by no of available threads
        for(int i = 0; i < taskCountToExecute; i++){
            futures.add(
                    executorService.submit(()->{
                        PurchaseOrderCreateRequestDto requestDto = PurchaseOrderCreateRequestDto.builder()
                                .purchaseDate(LocalDate.now())
                                .supplierId(4L)
                                .itemList(createPOItemList())
                                .build();

                        log.info("Submitting {}", Thread.currentThread().getName());
                        return purchaseOrderService.createPurchaseOrder(requestDto);
                    })
            );
        }

        for(Future<String> future : futures){
            log.info("{} executing {}", Thread.currentThread().getName(), future.get()); // wait for the task to finish otherwise, the main thread would finish and you won't see ....concurrent test end... message
        }
        executorService.shutdown();
        log.info("....concurrent test end...");
    }

    private List<PurchaseOrderItemCreateRequestDto> createPOItemList() {
        List<PurchaseOrderItemCreateRequestDto> itemList = new ArrayList<>();
        PurchaseOrderItemCreateRequestDto item1 = PurchaseOrderItemCreateRequestDto.builder()
                .itemId(1L)
                .price(new BigDecimal(300))
                .qty(new BigDecimal("0.5"))
                .build();

        itemList.add(item1);

        return itemList;
    }

}


/*
 * Concurrency test notes
 *
 * ExecutorService (pool of 20 threads)
 *  - The pool only provides workers. It does not create work.
 *  - Each executor.submit(task) runs ONE task on a free worker thread.
 *  - So the loop submits 20 tasks, and the workers run them at the same time.
 *  - submit() does not wait for the task. It returns a Future right away.
 *
 * Future (a ticket for the task's result)
 *  - future.get() blocks only the calling (main test) thread until that one task finishes.
 *  - It does not slow down the worker threads.
 *  - If the task threw an exception, get() rethrows it as ExecutionException,
 *    so the test fails with the real cause.
 *  - Calling get() on every future means the assertion runs only after all tasks are done.
 *
 * executor.shutdown()
 *  - Accepts no new tasks and lets running tasks finish. It does not block.
 *  - Needed so idle worker threads do not stay alive after the test.
 *  - Put it in a finally block so it runs even when get() throws.
 *
 * Test rules
 *  - No @Transactional on the test class or method. Worker threads use their own
 *    transactions, so test data must already be committed.
 *  - Clean up created rows afterwards, because nothing is rolled back.
 *  - Use a real database (PostgreSQL via Testcontainers), not H2.
 *  - Use @RepeatedTest because races depend on timing and one passing run proves little.
 *  - Check the test can fail: break the form number logic on purpose and confirm it goes red.
 */
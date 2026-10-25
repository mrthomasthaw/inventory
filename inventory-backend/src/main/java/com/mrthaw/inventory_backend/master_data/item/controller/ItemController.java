package com.mrthaw.inventory_backend.master_data.item.controller;


import com.mrthaw.inventory_backend.common.response.ApiResponse;
import com.mrthaw.inventory_backend.common.util.PageResult;
import com.mrthaw.inventory_backend.master_data.item.dto.ItemCreateRequestDto;
import com.mrthaw.inventory_backend.master_data.item.dto.ItemResponseDto;
import com.mrthaw.inventory_backend.master_data.item.dto.ItemSearchRequestDto;
import com.mrthaw.inventory_backend.master_data.item.dto.ItemUpdateRequestDto;
import com.mrthaw.inventory_backend.master_data.item.service.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/items")
@CrossOrigin(origins = "http://localhost:8080")
@RequiredArgsConstructor
public class ItemController {
    
    private final ItemService itemService;

    @GetMapping
    public ApiResponse<List<ItemResponseDto>> getItems() {
        return ApiResponse.success(itemService.getAllItems());
    }

    @PostMapping("/search")
    public ApiResponse<PageResult<ItemResponseDto>> searchItems(@RequestBody ItemSearchRequestDto searchRequestDto) {
        return ApiResponse.success(itemService.searchItems(searchRequestDto));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<Map<String, Long>> createItem(@Valid @RequestBody ItemCreateRequestDto createRequestDto) {
        Long id = itemService.createItem(createRequestDto);
        return ApiResponse.success(Map.of("itemId", id));
    }

    @PatchMapping
    public ApiResponse<Void> updateItem(@Valid @RequestBody ItemUpdateRequestDto updateRequestDto) {
        itemService.updateItem(updateRequestDto);
        return ApiResponse.success();
    }
}

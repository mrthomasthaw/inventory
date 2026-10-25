package com.mrthaw.inventory_backend.master_data.item.service;

import com.mrthaw.inventory_backend.common.util.PageResult;
import com.mrthaw.inventory_backend.master_data.item.dto.ItemCreateRequestDto;
import com.mrthaw.inventory_backend.master_data.item.dto.ItemResponseDto;
import com.mrthaw.inventory_backend.master_data.item.dto.ItemSearchRequestDto;
import com.mrthaw.inventory_backend.master_data.item.dto.ItemUpdateRequestDto;
import jakarta.validation.Valid;

import java.util.List;

public interface ItemService {

    List<ItemResponseDto> getAllItems();

    PageResult<ItemResponseDto> searchItems(ItemSearchRequestDto searchRequestDto);

    Long createItem(@Valid ItemCreateRequestDto createRequestDto);

    void updateItem(@Valid ItemUpdateRequestDto updateRequestDto);
}

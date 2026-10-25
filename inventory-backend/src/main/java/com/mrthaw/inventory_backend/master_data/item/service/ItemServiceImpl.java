package com.mrthaw.inventory_backend.master_data.item.service;

import com.mrthaw.inventory_backend.common.exception.DataNotFoundException;
import com.mrthaw.inventory_backend.common.exception.DuplicateDataException;
import com.mrthaw.inventory_backend.common.util.CommonUtils;
import com.mrthaw.inventory_backend.common.util.PageResult;
import com.mrthaw.inventory_backend.master_data.item.dto.ItemCreateRequestDto;
import com.mrthaw.inventory_backend.master_data.item.dto.ItemResponseDto;
import com.mrthaw.inventory_backend.master_data.item.dto.ItemSearchRequestDto;
import com.mrthaw.inventory_backend.master_data.item.dto.ItemUpdateRequestDto;
import com.mrthaw.inventory_backend.master_data.item.entity.Item;
import com.mrthaw.inventory_backend.master_data.item.jpa_specification.ItemSpecification;
import com.mrthaw.inventory_backend.master_data.item.repo.ItemRepo;
import com.mrthaw.inventory_backend.master_data.main_category.entity.MainCategory;
import com.mrthaw.inventory_backend.master_data.unit.entity.Unit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepo itemRepo;

    @Override
    public List<ItemResponseDto> getAllItems() {
        return itemRepo.findAll()
                .stream()
                .map(ItemResponseDto::toDto)
                .toList();
    }

    @Override
    public PageResult<ItemResponseDto> searchItems(ItemSearchRequestDto searchRequestDto) {
        return PageResult.toPageResult(itemRepo.findAll(
                ItemSpecification.createSearchSpecification(searchRequestDto)
                , PageResult.createPageable(searchRequestDto, Sort.by("id").descending())), ItemResponseDto::toDto);
    }



    @Override
    public Long createItem(ItemCreateRequestDto createRequestDto) {

        if(itemRepo.existsByItemCode(createRequestDto.getItemCode()))
            throw new DuplicateDataException("Item code already exist");

        Item item = Item.builder()
                .itemCode(createRequestDto.getItemCode())
                .itemName(createRequestDto.getItemName())
                .description(createRequestDto.getDescription())
                .status(createRequestDto.getStatus())
                .mainCategory(new MainCategory(createRequestDto.getMainCategoryId()))
                .unit(new Unit(createRequestDto.getUnitId()))
                .build();

        itemRepo.save(item);

        return item.getId();
    }

    @Override
    public void updateItem(ItemUpdateRequestDto updateRequestDto) {

        Item item = itemRepo.findById(updateRequestDto.getId()).
                orElseThrow(() -> new DataNotFoundException("Item not found"));


        if(CommonUtils.isValidString(updateRequestDto.getItemCode())) {

            if(itemRepo.existsByItemCodeAndIdNot(updateRequestDto.getItemCode(), updateRequestDto.getId()))
                throw new DuplicateDataException("Item code already exist");

            item.setItemCode(updateRequestDto.getItemCode());
        }


        if(CommonUtils.isValidString(updateRequestDto.getItemName()))
            item.setItemName(updateRequestDto.getItemName());

        if(CommonUtils.isValidString(updateRequestDto.getDescription()))
            item.setDescription(updateRequestDto.getDescription());

        if(updateRequestDto.getStatus() != null)
            item.setStatus(updateRequestDto.getStatus());

        if(updateRequestDto.getMainCategoryId() != null)
            item.setMainCategory(new MainCategory(updateRequestDto.getMainCategoryId()));

        if(updateRequestDto.getUnitId() != null)
            item.setUnit(new Unit(updateRequestDto.getUnitId()));

        itemRepo.save(item);
    }
}

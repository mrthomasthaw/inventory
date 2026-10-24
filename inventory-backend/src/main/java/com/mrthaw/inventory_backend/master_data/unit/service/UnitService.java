package com.mrthaw.inventory_backend.master_data.unit.service;


import com.mrthaw.inventory_backend.master_data.unit.dto.UnitCreateRequestDto;
import com.mrthaw.inventory_backend.master_data.unit.dto.UnitResponseDto;
import com.mrthaw.inventory_backend.master_data.unit.dto.UnitUpdateRequestDto;
import jakarta.validation.Valid;

import java.util.List;

public interface UnitService {

    List<UnitResponseDto> getAllUnits();

    Long createUnit(@Valid UnitCreateRequestDto createRequestDto);

    void updateUnit(@Valid UnitUpdateRequestDto updateRequestDto);
}

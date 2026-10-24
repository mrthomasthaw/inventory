package com.mrthaw.inventory_backend.master_data.user_role.service;

import com.mrthaw.inventory_backend.master_data.user_role.dto.UserRoleCreateRequestDto;
import com.mrthaw.inventory_backend.master_data.user_role.dto.UserRoleResponseDto;
import com.mrthaw.inventory_backend.master_data.user_role.dto.UserRoleUpdateRequestDto;
import jakarta.validation.Valid;

import java.util.List;

public interface UserRoleService {

    Long createUserRole(UserRoleCreateRequestDto createRequestDto);

    void updateUserRole(@Valid UserRoleUpdateRequestDto updateRequestDto);

    List<UserRoleResponseDto> getAllUserRoles();
}

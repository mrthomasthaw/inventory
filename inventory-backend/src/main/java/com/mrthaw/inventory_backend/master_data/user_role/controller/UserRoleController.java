package com.mrthaw.inventory_backend.master_data.user_role.controller;

import com.mrthaw.inventory_backend.common.response.ApiResponse;
import com.mrthaw.inventory_backend.master_data.user_role.dto.UserRoleCreateRequestDto;
import com.mrthaw.inventory_backend.master_data.user_role.dto.UserRoleResponseDto;
import com.mrthaw.inventory_backend.master_data.user_role.dto.UserRoleUpdateRequestDto;
import com.mrthaw.inventory_backend.master_data.user_role.service.UserRoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/user-roles")
@CrossOrigin(origins = "http://localhost:8080")
@RequiredArgsConstructor
public class UserRoleController {

    private final UserRoleService userRoleService;

    @GetMapping
    public ApiResponse<List<UserRoleResponseDto>> getUserRoles() {
        return ApiResponse.success(userRoleService.getAllUserRoles());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<Map<String, Long>> createUserRole(@Valid @RequestBody UserRoleCreateRequestDto createRequestDto) {
        Long id = userRoleService.createUserRole(createRequestDto);
        return ApiResponse.success(Map.of("roleId", id));
    }

    @PatchMapping
    public ApiResponse<Void> updateUserRole(@Valid @RequestBody UserRoleUpdateRequestDto updateRequestDto) {
        userRoleService.updateUserRole(updateRequestDto);
        return ApiResponse.success();
    }
}

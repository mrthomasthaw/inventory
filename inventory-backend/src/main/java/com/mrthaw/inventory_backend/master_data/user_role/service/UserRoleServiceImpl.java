package com.mrthaw.inventory_backend.master_data.user_role.service;

import com.mrthaw.inventory_backend.common.exception.DataNotFoundException;
import com.mrthaw.inventory_backend.common.util.CommonUtils;
import com.mrthaw.inventory_backend.master_data.user_role.dto.UserRoleCreateRequestDto;
import com.mrthaw.inventory_backend.master_data.user_role.dto.UserRoleResponseDto;
import com.mrthaw.inventory_backend.master_data.user_role.dto.UserRoleUpdateRequestDto;
import com.mrthaw.inventory_backend.master_data.user_role.entity.UserRole;
import com.mrthaw.inventory_backend.master_data.user_role.repo.UserRoleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRoleRepo userRoleRepo;


    @Override
    public Long createUserRole(UserRoleCreateRequestDto createRequestDto) {
        UserRole userRole = UserRole.builder()
                        .code(createRequestDto.getCode())
                        .name(createRequestDto.getName())
                        .status(createRequestDto.getStatus())
                        .build();


        userRoleRepo.save(userRole);
        return userRole.getId();
    }

    @Override
    public void updateUserRole(UserRoleUpdateRequestDto updateRequestDto) {

        UserRole userRole = userRoleRepo.findById(updateRequestDto.getId())
                .orElseThrow(() -> new DataNotFoundException("User role not found"));

        if(CommonUtils.isValidString(updateRequestDto.getCode()))
            userRole.setCode(updateRequestDto.getCode());

        if(CommonUtils.isValidString(updateRequestDto.getName()))
            userRole.setName(updateRequestDto.getName());

        if(updateRequestDto.getStatus() != null)
            userRole.setStatus(updateRequestDto.getStatus());

        userRoleRepo.save(userRole);
    }

    @Override
    public List<UserRoleResponseDto> getAllUserRoles() {
        return userRoleRepo.findAll()
                .stream()
                .map(UserRoleResponseDto::toDto)
                .toList();
    }
}

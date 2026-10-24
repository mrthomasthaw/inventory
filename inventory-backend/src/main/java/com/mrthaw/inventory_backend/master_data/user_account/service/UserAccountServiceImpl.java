package com.mrthaw.inventory_backend.master_data.user_account.service;

import com.mrthaw.inventory_backend.common.exception.DataNotFoundException;
import com.mrthaw.inventory_backend.common.util.CommonUtils;
import com.mrthaw.inventory_backend.common.util.PageResult;
import com.mrthaw.inventory_backend.master_data.user_account.dto.UserAccountCreateRequestDto;
import com.mrthaw.inventory_backend.master_data.user_account.dto.UserAccountResponseDto;
import com.mrthaw.inventory_backend.master_data.user_account.dto.UserAccountSearchRequestDto;
import com.mrthaw.inventory_backend.master_data.user_account.dto.UserAccountUpdateRequestDto;
import com.mrthaw.inventory_backend.master_data.user_account.entity.UserAccount;
import com.mrthaw.inventory_backend.master_data.user_account.jpa_specification.UserAccountSpecification;
import com.mrthaw.inventory_backend.master_data.user_account.repo.UserAccountRepo;
import com.mrthaw.inventory_backend.master_data.user_role.entity.UserRole;
import com.mrthaw.inventory_backend.master_data.user_role.repo.UserRoleRepo;
import jdk.jfr.consumer.RecordedStackTrace;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserAccountServiceImpl implements UserAccountService {

    private final UserAccountRepo userAccountRepo;

    private final UserRoleRepo userRoleRepo;

    @Override
    public List<UserAccountResponseDto> getAllUserAccounts() {
        return userAccountRepo.findAll()
                .stream()
                .map(UserAccountResponseDto::toDto)
                .toList();
    }

    @Override
    public Long createUserAccount(UserAccountCreateRequestDto createRequestDto) {
        UserAccount userAccount = UserAccount.builder()
                .name(createRequestDto.getName())
                .profileName(createRequestDto.getProfileName())
                .password(createRequestDto.getPassword())
                .status(createRequestDto.getStatus())
                .userRole(new UserRole(createRequestDto.getUserRoleId()))
                .build();

        //Need to hash the password

        //Need to set created time and updated time
        userAccountRepo.save(userAccount);

        return userAccount.getId();
    }

    @Override
    public void updateUserAccount(UserAccountUpdateRequestDto updateRequestDto) {

        UserAccount userAccount = userAccountRepo.findById(updateRequestDto.getId())
                .orElseThrow(() -> new DataNotFoundException("User not found"));

        if(CommonUtils.isValidString(updateRequestDto.getName()))
            userAccount.setName(updateRequestDto.getName());

        if(CommonUtils.isValidString(updateRequestDto.getProfileName()))
            userAccount.setProfileName(updateRequestDto.getProfileName());

        if(updateRequestDto.getStatus() != null)
            userAccount.setStatus(updateRequestDto.getStatus());

        if(updateRequestDto.getUserRoleId() != null)
            userAccount.setUserRole(userRoleRepo.findById(updateRequestDto.getUserRoleId())
                    .orElseThrow(() -> new DataNotFoundException("User Role not found")));

        userAccountRepo.save(userAccount);
    }

    @Override
    public PageResult<UserAccountResponseDto> searchUserAccounts(UserAccountSearchRequestDto searchRequestDto) {
        return PageResult.toPageResult(userAccountRepo.findAll(UserAccountSpecification.createSearchSpecification(searchRequestDto),
                PageResult.createPageable(searchRequestDto, Sort.by("id").descending())), UserAccountResponseDto::toDto);
    }


}

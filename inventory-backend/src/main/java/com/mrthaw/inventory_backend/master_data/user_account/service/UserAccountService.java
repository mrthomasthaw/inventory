package com.mrthaw.inventory_backend.master_data.user_account.service;


import com.mrthaw.inventory_backend.common.util.PageResult;
import com.mrthaw.inventory_backend.master_data.user_account.dto.UserAccountCreateRequestDto;
import com.mrthaw.inventory_backend.master_data.user_account.dto.UserAccountResponseDto;
import com.mrthaw.inventory_backend.master_data.user_account.dto.UserAccountSearchRequestDto;
import com.mrthaw.inventory_backend.master_data.user_account.dto.UserAccountUpdateRequestDto;

import java.util.List;

public interface UserAccountService {

    List<UserAccountResponseDto> getAllUserAccounts();

    Long createUserAccount(UserAccountCreateRequestDto createRequestDto);

    void updateUserAccount(UserAccountUpdateRequestDto updateRequestDto);

    PageResult<UserAccountResponseDto> searchUserAccounts(UserAccountSearchRequestDto searchRequestDto);
}

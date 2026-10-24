package com.mrthaw.inventory_backend.master_data.user_account.controller;

import com.mrthaw.inventory_backend.common.response.ApiResponse;
import com.mrthaw.inventory_backend.common.util.PageResult;
import com.mrthaw.inventory_backend.master_data.user_account.dto.UserAccountCreateRequestDto;
import com.mrthaw.inventory_backend.master_data.user_account.dto.UserAccountResponseDto;
import com.mrthaw.inventory_backend.master_data.user_account.dto.UserAccountSearchRequestDto;
import com.mrthaw.inventory_backend.master_data.user_account.dto.UserAccountUpdateRequestDto;
import com.mrthaw.inventory_backend.master_data.user_account.entity.UserAccount;
import com.mrthaw.inventory_backend.master_data.user_account.service.UserAccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/api/v1/user-accounts")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8080")
public class UserAccountController {

    private final UserAccountService userAccountService;

    @GetMapping
    public ApiResponse<List<UserAccountResponseDto>> getUserAccounts() {
        return ApiResponse.success(userAccountService.getAllUserAccounts());
    }

    @PostMapping("/search")
    public ApiResponse<PageResult<UserAccountResponseDto>> searchUserAccounts(@RequestBody UserAccountSearchRequestDto searchRequestDto) {
        return ApiResponse.success(userAccountService.searchUserAccounts(searchRequestDto));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<Map<String, Long>> createUserAccount(@Valid @RequestBody UserAccountCreateRequestDto createRequestDto) {
        Long id = userAccountService.createUserAccount(createRequestDto);
        return ApiResponse.success(Map.of("userId", id));
    }

    @PatchMapping
    public ApiResponse<Void> updateUserAccount(@Valid @RequestBody UserAccountUpdateRequestDto updateRequestDto) {
        userAccountService.updateUserAccount(updateRequestDto);
        return ApiResponse.success();
    }
}

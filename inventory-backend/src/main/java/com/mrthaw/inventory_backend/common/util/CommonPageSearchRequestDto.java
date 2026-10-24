package com.mrthaw.inventory_backend.common.util;

import com.mrthaw.inventory_backend.master_data.user_account.dto.UserAccountSearchRequestDto;
import lombok.Getter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Getter
public class CommonPageSearchRequestDto {

    private int page = 0;

    private int pageSize = 10;
}

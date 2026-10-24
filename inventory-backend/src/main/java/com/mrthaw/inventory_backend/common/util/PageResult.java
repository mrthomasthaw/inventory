package com.mrthaw.inventory_backend.common.util;

import com.mrthaw.inventory_backend.master_data.business_partner.dto.BusinessPartnerResponseDto;
import com.mrthaw.inventory_backend.master_data.user_account.dto.UserAccountSearchRequestDto;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


import java.util.List;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<T> {

    private List<T> list;

    private Long totalCount;

    private Integer page;

    private Integer pageSize;

    public static <E,T> PageResult<T> toPageResult(@NotNull Page<E> page, Function<E, T> mapper) {

        return PageResult.<T>builder()
                .page(page.getNumber())
                .pageSize(page.getSize())
                .list(page.getContent()
                        .stream()
                        .map(mapper)
                        .toList())
                .totalCount(page.getTotalElements())
                .build();
    }

    public static Pageable createPageable(CommonPageSearchRequestDto searchRequestDto, Sort sort) {
        return PageRequest.of(Math.max(searchRequestDto.getPage(), 0), searchRequestDto.getPageSize(), sort);
    }

}

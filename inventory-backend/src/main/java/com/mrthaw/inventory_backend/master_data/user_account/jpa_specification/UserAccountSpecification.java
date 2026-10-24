package com.mrthaw.inventory_backend.master_data.user_account.jpa_specification;

import com.mrthaw.inventory_backend.common.util.CommonUtils;
import com.mrthaw.inventory_backend.master_data.user_account.dto.UserAccountSearchRequestDto;
import com.mrthaw.inventory_backend.master_data.user_account.entity.UserAccount;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class UserAccountSpecification {

    public static Specification<UserAccount> createSearchSpecification(UserAccountSearchRequestDto searchRequestDto) {

        return ((root, query, cb) -> {
           List<Predicate> predicates = new ArrayList<>();

           if(CommonUtils.isValidString(searchRequestDto.getName()))
               predicates.add(cb.like(root.get("name").as(String.class), "%" + searchRequestDto.getName() + "%"));

           if(CommonUtils.isValidString(searchRequestDto.getProfileName()))
               predicates.add(cb.like(root.get("profileName").as(String.class), "%" + searchRequestDto.getProfileName() + "%"));

           if(searchRequestDto.getStatus() != null)
               predicates.add(cb.equal(root.get("status"), searchRequestDto.getStatus()));

           if(searchRequestDto.getUserRoleId() != null)
               predicates.add(cb.equal(root.get("userRole").get("id"), searchRequestDto.getUserRoleId()));

           return cb.and(predicates.toArray(new Predicate[0]));
        });
    }
}

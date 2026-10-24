package com.mrthaw.inventory_backend;

import com.mrthaw.inventory_backend.master_data.user_role.dto.UserRoleCreateRequestDto;
import com.mrthaw.inventory_backend.master_data.user_role.entity.UserRole;
import com.mrthaw.inventory_backend.master_data.user_role.repo.UserRoleRepo;
import com.mrthaw.inventory_backend.master_data.user_role.service.UserRoleService;
import com.mrthaw.inventory_backend.master_data.user_role.service.UserRoleServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserRoleServiceTest {

    @Mock
    UserRoleRepo repository;

    @InjectMocks
    UserRoleServiceImpl service;

    @Test
    void create_user_role_and_return_role_id() {
        when(repository.save(any(UserRole.class))) // When this repo save any of the userRole instances, return role
                .thenAnswer(inv -> {
                    UserRole role = inv.getArgument(0);
                    role.setId(1L);
                    return role;
                });

        UserRoleCreateRequestDto createRequestDto = new UserRoleCreateRequestDto();
        createRequestDto.setCode("admin");
        createRequestDto.setName("controller");

        Long roleId = service.createUserRole(createRequestDto);

        assertThat(roleId).isEqualTo(1L);

        ArgumentCaptor<UserRole> captor = ArgumentCaptor.forClass(UserRole.class);

        verify(repository).save(captor.capture());

        UserRole userRole = captor.getValue();
        assertThat(userRole.getCode()).isEqualTo("admin");
        assertThat(userRole.getName()).isEqualTo("controller");
    }
}

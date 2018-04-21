package com.dspassov.kovapi.service;

import com.dspassov.kovapi.areas.users.entities.Role;
import com.dspassov.kovapi.areas.users.enumerations.RoleName;
import com.dspassov.kovapi.areas.users.models.service.RoleServiceModel;
import com.dspassov.kovapi.areas.users.services.RoleServiceImpl;
import com.dspassov.kovapi.repositories.RoleRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import org.modelmapper.ModelMapper;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.concurrent.atomic.AtomicInteger;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class RoleServiceTests {

    @Mock
    private RoleRepository roleRepository;

    private RoleServiceImpl roleService;

    @Before
    public void init() {
        this.roleService = new RoleServiceImpl(this.roleRepository, new ModelMapper());
    }


    @Test
    public void testInitiateRoles_simulateApplicationStartWithNoRoles_expectAllRolesToBeCreated() {


        //arrange
        AtomicInteger savedRolesCounter = new AtomicInteger();
        when(this.roleRepository.save(any()))
                .thenAnswer(a -> {
                    savedRolesCounter.getAndIncrement();
                    return a.getArgument(0);
                });

        //act
        this.roleService.initiateRoles();

        int expected = RoleName.values().length;
        int actual = savedRolesCounter.get();

        //assert
        assertEquals(
                "Roles are not initialized correctly",
                expected,
                actual
        );
    }

    @Test
    public void testGetRole_inquireExistingRole_expectMappedRoleServiceModel() {

        //arrange
        when(this.roleRepository.findByRoleName(any()))
                .thenAnswer(a -> {
                    Role entity = new Role();
                    entity.setRoleName(RoleName.ROLE_ADMIN);
                    return entity;
                });

        //act
        RoleServiceModel role = this.roleService.getRole(RoleName.ROLE_ADMIN);

        assertEquals(
                "Role not mapped correctly",
                RoleName.ROLE_ADMIN,
                role.getRole()
        );
    }

    @Test
    public void testGetRole_inquireNonExistentRole_expectRoleToBeNull() {
        //arrange
        when(this.roleRepository.findByRoleName(any()))
                .thenAnswer(a -> null);

        //act
        RoleServiceModel role = this.roleService.getRole(RoleName.ROLE_ADMIN);

        assertNull("Role is not null", role);
    }
}

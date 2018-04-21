package com.dspassov.kovapi.service;

import com.dspassov.kovapi.areas.game.models.service.HeroServiceModel;
import com.dspassov.kovapi.areas.game.services.HeroService;
import com.dspassov.kovapi.areas.users.entities.Role;
import com.dspassov.kovapi.areas.users.entities.User;
import com.dspassov.kovapi.areas.users.enumerations.RoleName;
import com.dspassov.kovapi.areas.users.models.binding.RegisterUserBindingModel;
import com.dspassov.kovapi.areas.users.models.service.RoleServiceModel;
import com.dspassov.kovapi.areas.users.models.view.UserPageViewModel;
import com.dspassov.kovapi.areas.users.services.RoleService;
import com.dspassov.kovapi.areas.users.services.UserServiceImpl;
import com.dspassov.kovapi.exceptions.IllegalParamException;
import com.dspassov.kovapi.repositories.UserRepository;
import com.dspassov.kovapi.web.ResponseMessageConstants;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.*;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private HeroService heroService;

    @Mock
    private RoleService roleService;

    private UserServiceImpl userService;

    @Before
    public void init() {
        this.userService = new UserServiceImpl(
                this.userRepository,
                new BCryptPasswordEncoder(),
                new ModelMapper(),
                this.heroService,
                this.roleService
        );

        when(this.userRepository.save(any()))
                .thenAnswer(a -> null);
    }

    @Test
    public void testSave_passCorrectData_expectRegistrationSuccessfulResponse() {

        //arrange
        when(this.userRepository.findByUsername(any()))
                .thenAnswer(a -> null);

        when(this.heroService.findHeroByName(any()))
                .thenAnswer(a -> null);

        when(this.heroService.createNewHero(any()))
                .thenAnswer(a -> new HeroServiceModel());

        when(this.roleService.getRole(any()))
                .thenAnswer(a -> new RoleServiceModel());

        RegisterUserBindingModel userBindingModel = new RegisterUserBindingModel();
        userBindingModel.setHeroName("Arthas");
        userBindingModel.setUsername("test@kov.com");
        userBindingModel.setPassword("123123");
        userBindingModel.setConfirmPassword("123123");

        //act
        String response = this.userService.save(userBindingModel);

        //assert
        assertEquals(
                "User registration failed",
                ResponseMessageConstants.REGISTRATION_SUCCESSFUL,
                response);

    }

    @Test
    public void testSave_passwordsMismatch_shouldThrowException() {


        //arrange
        RegisterUserBindingModel userBindingModel = new RegisterUserBindingModel();
        userBindingModel.setHeroName("Arthas");
        userBindingModel.setUsername("test@kov.com");
        userBindingModel.setPassword("123456");
        userBindingModel.setConfirmPassword("12123");

        try {
            //act
            this.userService.save(userBindingModel);
            fail("Expected IllegalParamException");
        } catch (IllegalParamException ipe) {

            //assert
            assertEquals(
                    "Password mismatch was not caught",
                    ResponseMessageConstants.PASSWORDS_MISMATCH,
                    ipe.getMessage()
            );
        }
    }

    @Test
    public void testSave_existingUser_shouldThrowException() {

        //arrange
        RegisterUserBindingModel userBindingModel = new RegisterUserBindingModel();
        userBindingModel.setUsername("test@kov.com");
        userBindingModel.setPassword("12345");
        userBindingModel.setConfirmPassword("12345");

        when(this.userRepository.findByUsername(any()))
                .thenAnswer(a -> new User());

        try {
            //act
            this.userService.save(userBindingModel);
            fail("Expected IllegalParamException");
        } catch (IllegalParamException ipe) {

            //assert
            assertEquals(
                    "Existing user error message was not returned",
                    ResponseMessageConstants.EXISTING_USER,
                    ipe.getMessage()
            );
        }
    }

    @Test
    public void testSave_existingHero_shouldThrowException() {
        //arrange
        RegisterUserBindingModel userBindingModel = new RegisterUserBindingModel();
        userBindingModel.setHeroName("Arthas");
        userBindingModel.setPassword("12345");
        userBindingModel.setConfirmPassword("12345");

        when(this.userRepository.findByUsername(any()))
                .thenAnswer(a -> null);

        when(this.heroService.findHeroByName(any()))
                .thenAnswer(a -> new HeroServiceModel());

        try {
            //act
            this.userService.save(userBindingModel);
            fail("Expected IllegalParamException");
        } catch (IllegalParamException ipe) {

            //assert
            assertEquals(
                    "Existing hero error message was not returned",
                    ResponseMessageConstants.EXISTING_HERO,
                    ipe.getMessage()
            );
        }
    }

    @Test
    public void testGetAllAdminUsers_correctInput_expectCorrectResult() {

        //arrange
        when(this.roleService.getRole(any()))
                .thenAnswer(a -> {
                    RoleServiceModel role = new RoleServiceModel();
                    role.setRole(RoleName.ROLE_ADMIN);
                    return role;
                });

        Role adminRole = new Role();
        adminRole.setRoleName(RoleName.ROLE_ADMIN);

        final int PAGE_NUMBER = 0;
        final Integer PAGE_SIZE = 5;
        final int ADMIN_USERS_TOTAL = 5;

        PageRequest pageRequest = PageRequest.of(PAGE_NUMBER, PAGE_SIZE);
        List<User> adminUsers = new ArrayList<>();

        User userOne = new User();
        userOne.setUsername("testUser");
        Set<Role> roles = new HashSet<>();
        roles.add(adminRole);
        userOne.setRoles(roles);

        User userTwo = new User();
        userTwo.setRoles(roles);
        userTwo.setUsername("testAdmin");
        adminUsers.add(userOne);
        adminUsers.add(userTwo);

        Page<User> admins = new PageImpl<>(adminUsers, pageRequest, ADMIN_USERS_TOTAL);

        when(this.userRepository.findAllByRolesContaining(any(), any()))
                .thenAnswer(a -> admins);


        //act
        UserPageViewModel page = this.userService.getAllAdminUsers(PAGE_NUMBER, PAGE_SIZE);


        //assert

        assertEquals(
                "Page size is incorrect",
                PAGE_SIZE,
                page.getSize()
        );

        Integer expectedAllPagesCount = ADMIN_USERS_TOTAL / PAGE_SIZE;

        assertEquals(
                "All pages count is incorrect",
                expectedAllPagesCount,
                page.getAllPages()
        );


        assertEquals(
                "Not all admin users are returned",
                adminUsers.size(),
                page.getUsers().size()
        );
    }

    @Test
    public void testMakeAdmin_passRegularUser_expectSuccessResponse() {

        //arrange
        Role role = new Role();
        role.setRoleName(RoleName.ROLE_USER);

        User user = new User();
        Set<Role> roles = new HashSet<>();

        roles.add(role);
        user.setRoles(roles);

        when(this.userRepository.findByUsername(any()))
                .thenAnswer(a -> user);

        when(this.roleService.getRole(RoleName.ROLE_ADMIN))
                .thenAnswer(a -> {
                    RoleServiceModel model = new RoleServiceModel();
                    model.setRole(RoleName.ROLE_ADMIN);
                    return model;
                });

        //act
        String expected = ResponseMessageConstants.ADMIN_CREATED;
        String actual = this.userService.makeAdmin("testUser");

        assertEquals(
                "User is not made admin",
                expected,
                actual
        );
    }

    @Test
    public void testMakeAdmin_passNonExistentser_shouldThrowException() {

        //arrange

        when(this.userRepository.findByUsername(any()))
                .thenAnswer(a -> null);

        try {
            //act
            this.userService.makeAdmin("testUser");
            fail("Expected IllegalParamException");
        } catch (IllegalParamException ipe) {
            assertEquals(
                    "Incorrect error message",
                    ResponseMessageConstants.NON_EXISTENT_USER,
                    ipe.getMessage()

            );
        }
    }

    @Test
    public void testMakeAdmin_passAdminUsername_shouldThrowException() {

        //arrange
        when(this.userRepository.findByUsername(any()))
                .thenAnswer(a -> this.getAdminUserMock());

        try {
            //act
            this.userService.makeAdmin("testUser");
            fail("Expected IllegalParamException");
        } catch (IllegalParamException ipe) {
            assertEquals(
                    "Incorrect error message",
                    ResponseMessageConstants.ALREADY_ADMIN,
                    ipe.getMessage()

            );
        }
    }

    @Test
    public void testRemoveAdmin_passAdmin_expectSuccessResponse() {

        //arrange
        when(this.userRepository.findByUsername(any()))
                .thenAnswer(a -> this.getAdminUserMock());

        //assert
        assertEquals(
                "Admin role not removed",
                ResponseMessageConstants.ADMIN_REMOVED,
                this.userService.removeAdmin("testUser")
        );
    }

    @Test
    public void testRemoveAdmin_nonExistentUser_shouldThrowException() {

        //arrange
        when(this.userRepository.findByUsername(any()))
                .thenAnswer(a -> null);

        try {
            //act
            this.userService.removeAdmin("testUser");
            fail("Expected IllegalParamException");

        } catch (IllegalParamException ipe) {
            //assert
            assertEquals(
                    "Incorrect error message",
                    ResponseMessageConstants.NON_EXISTENT_USER,
                    ipe.getMessage()
            );
        }
    }

    @Test
    public void testRemoveAdmin_nonExistentAdmin_shouldThrowException() {

        //arrange
        User user = new User();
        Role role = new Role();
        role.setRoleName(RoleName.ROLE_USER);
        user.setRoles(Collections.singleton(role));

        when(this.userRepository.findByUsername(any()))
                .thenAnswer(a -> user);

        try {
            //act
            this.userService.removeAdmin("testUser");
            fail("Expected IllegalParamException");

        } catch (IllegalParamException ipe) {
            //assert
            assertEquals(
                    "Incorrect error message",
                    ResponseMessageConstants.USER_NOT_ADMIN,
                    ipe.getMessage()
            );
        }
    }

    private User getAdminUserMock() {
        Role adminRole = new Role();
        adminRole.setRoleName(RoleName.ROLE_ADMIN);

        User admin = new User();
        admin.setRoles(Collections.singleton(adminRole));

        return admin;
    }
}

package com.dspassov.kovapi.areas.users.services;

import com.dspassov.kovapi.areas.game.entities.Hero;
import com.dspassov.kovapi.areas.game.models.service.HeroServiceModel;
import com.dspassov.kovapi.areas.game.services.HeroService;
import com.dspassov.kovapi.areas.users.entities.Role;
import com.dspassov.kovapi.areas.users.entities.User;
import com.dspassov.kovapi.areas.users.enumerations.RoleName;
import com.dspassov.kovapi.areas.users.models.binding.RegisterUserBindingModel;
import com.dspassov.kovapi.areas.users.models.service.RoleServiceModel;
import com.dspassov.kovapi.areas.users.models.view.UserPageViewModel;
import com.dspassov.kovapi.areas.users.models.view.UserViewModel;
import com.dspassov.kovapi.exceptions.IllegalParamException;
import com.dspassov.kovapi.repositories.UserRepository;
import com.dspassov.kovapi.web.ResponseMessageConstants;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final HeroService heroService;
    private final RoleService roleService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           BCryptPasswordEncoder passwordEncoder,
                           ModelMapper modelMapper, HeroService heroService,
                           RoleService roleService) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.heroService = heroService;
        this.roleService = roleService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return this.userRepository.findByUsername(email);
    }


    @Override
    public String save(RegisterUserBindingModel model) {

        if (!model.getPassword().equals(model.getConfirmPassword())) {
            throw new IllegalParamException(ResponseMessageConstants.PASSWORDS_MISMATCH);
        }

        if (this.userRepository.findByUsername(model.getUsername()) != null) {
            throw new IllegalParamException(ResponseMessageConstants.EXISTING_USER);
        }

        if (this.heroService.findHeroByName(model.getHeroName()) != null) {
            throw new IllegalParamException(ResponseMessageConstants.EXISTING_HERO);
        }

        model.setPassword(this.passwordEncoder.encode(model.getPassword()));
        User user = this.modelMapper.map(model, User.class);
        HeroServiceModel newHero = this.heroService.createNewHero(model.getHeroName());
        Hero hero = this.modelMapper.map(newHero, Hero.class);
        user.setHero(hero);
        user.setRoles(this.getRolesForNewUser());

        this.userRepository.save(user);

        return ResponseMessageConstants.REGISTRATION_SUCCESSFUL;
    }


    @Override
    public UserPageViewModel getAllAdminUsers(int page, int size) {
        RoleServiceModel adminModel = this.roleService.getRole(RoleName.ROLE_ADMIN);
        Role admin = this.modelMapper.map(adminModel, Role.class);

        Page<User> admins = this.userRepository.findAllByRolesContaining(
                PageRequest.of(page, size), admin);

        UserPageViewModel adminsPage = this.modelMapper.map(admins, UserPageViewModel.class);

        adminsPage.setAllPages(admins.getTotalPages());
        adminsPage.setUsers(admins.getContent().stream().map(a -> {
            UserViewModel userModel = this.modelMapper.map(a, UserViewModel.class);

            userModel.setRole(this.getHighestRoleFrom(a.getRoles()).name());


            return userModel;
        }).collect(Collectors.toList()));

        return adminsPage;
    }

    @Override
    public String makeAdmin(String username) {
        User adminCandidate = this.userRepository.findByUsername(username);
        if (adminCandidate == null) {
            throw new IllegalParamException(ResponseMessageConstants.NON_EXISTENT_USER);
        }

        for (Role role : adminCandidate.getRoles()) {
            if (role.getRoleName().equals(RoleName.ROLE_ADMIN)) {
                throw new IllegalParamException(ResponseMessageConstants.ALREADY_ADMIN);
            }
        }

        RoleServiceModel adminRole = this.roleService.getRole(RoleName.ROLE_ADMIN);
        adminCandidate.getRoles().add(this.modelMapper.map(adminRole, Role.class));

        this.userRepository.save(adminCandidate);
        return ResponseMessageConstants.ADMIN_CREATED;
    }

    @Override
    public String removeAdmin(String username) {
        User admin = this.userRepository.findByUsername(username);
        if (admin == null) {
            throw new IllegalParamException(ResponseMessageConstants.NON_EXISTENT_USER);
        }

        Set<Role> newRoles = new HashSet<>();
        for (Role newRole : admin.getRoles()) {
            if (!newRole.getRoleName().equals(RoleName.ROLE_ADMIN)) {
                newRoles.add(newRole);
            }
        }

        if (newRoles.size() == admin.getRoles().size()) {
            throw new IllegalParamException(ResponseMessageConstants.USER_NOT_ADMIN);
        }

        admin.setRoles(newRoles);
        this.userRepository.save(admin);
        return ResponseMessageConstants.ADMIN_REMOVED;
    }

    private Set<Role> getRolesForNewUser() {
        List<User> allCurrentUsers = (List<User>) this.userRepository.findAll();
        Set<Role> roles = new HashSet<>();

        RoleServiceModel defaultRole = this.roleService.getRole(RoleName.ROLE_USER);
        roles.add(this.modelMapper.map(defaultRole, Role.class));

        if (allCurrentUsers.size() == 0) {
            defaultRole = this.roleService.getRole(RoleName.ROLE_ADMIN);
            roles.add(this.modelMapper.map(defaultRole, Role.class));
            defaultRole = this.roleService.getRole(RoleName.ROLE_SUPERADMIN);
            roles.add(this.modelMapper.map(defaultRole, Role.class));
        }

        return roles;
    }

    private RoleName getHighestRoleFrom(Set<Role> roles) {

        boolean admin = false;

        for (Role role : roles) {
            if (role.getRoleName().equals(RoleName.ROLE_SUPERADMIN)) {
                return RoleName.ROLE_SUPERADMIN;
            }

            if (role.getRoleName().equals(RoleName.ROLE_ADMIN)) {
                admin = true;
            }
        }

        return admin ? RoleName.ROLE_ADMIN : RoleName.ROLE_USER;
    }
}

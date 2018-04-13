package com.dspassov.kovapi.areas.users.services;

import com.dspassov.kovapi.areas.game.entities.Hero;
import com.dspassov.kovapi.areas.game.models.HeroServiceModel;
import com.dspassov.kovapi.areas.game.services.HeroService;
import com.dspassov.kovapi.areas.users.entities.Role;
import com.dspassov.kovapi.areas.users.entities.User;
import com.dspassov.kovapi.areas.users.enumerations.RoleName;
import com.dspassov.kovapi.areas.users.models.binding.RegisterUserBindingModel;
import com.dspassov.kovapi.areas.users.models.service.RoleServiceModel;
import com.dspassov.kovapi.repositories.UserRepository;
import com.dspassov.kovapi.web.ResponseMessageConstants;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


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
                           ModelMapper modelMapper, HeroService heroService, RoleService roleService) {

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
            throw new IllegalArgumentException(ResponseMessageConstants.PASSWORDS_MISMATCH);
        }

        if (this.userRepository.findByUsername(model.getUsername()) != null) {
            throw new IllegalArgumentException(ResponseMessageConstants.EXISTING_USER);
        }

        if (this.heroService.findHeroByName(model.getHeroName()) != null) {
            throw new IllegalArgumentException(ResponseMessageConstants.EXISTING_HERO);
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

}

package com.dspassov.kovapi.areas.users.services;

import com.dspassov.kovapi.areas.users.common.RoleFactory;
import com.dspassov.kovapi.areas.users.entities.Role;
import com.dspassov.kovapi.areas.users.enumerations.RoleName;
import com.dspassov.kovapi.areas.users.models.service.RoleServiceModel;
import com.dspassov.kovapi.repositories.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository, ModelMapper modelMapper) {
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void initiateRoles() {

        List<Role> currentRoles = this.roleRepository.findAll();

        if (currentRoles.size() != 3) {

            for (RoleName roleName : RoleName.values()) {

                if (this.roleRepository.findByRole(roleName) == null) {
                    this.roleRepository.save(RoleFactory.createNewRole(roleName));
                }
            }
        }
    }

    @Override
    public RoleServiceModel getRole(RoleName roleName) {
        Role role = this.roleRepository.findByRole(roleName);

        if (role != null) {
            return this.modelMapper.map(role, RoleServiceModel.class);
        }

        return null;
    }
}

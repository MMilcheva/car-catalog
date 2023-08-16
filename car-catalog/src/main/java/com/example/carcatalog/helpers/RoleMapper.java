package com.example.carcatalog.helpers;

import com.example.carcatalog.dto.RoleResponse;
import com.example.carcatalog.dto.RoleSaveRequest;
import com.example.carcatalog.models.Role;
import com.example.carcatalog.services.contracts.RoleService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RoleMapper {
private final RoleService roleService;
    public RoleMapper(RoleService roleService) {
        this.roleService = roleService;
    }

    public Role convertToRole(RoleSaveRequest roleSaveRequest) {
        Role role = new Role();
        role.setRoleName(roleSaveRequest.getRoleName());
        return role;
    }

    public RoleResponse convertToRoleResponse(Role role) {

        RoleResponse roleResponse = new RoleResponse();
        roleResponse.setRoleId(role.getRoleId());
        roleResponse.setRoleName(role.getRoleName());
        return roleResponse;
    }

    public Role convertToRoleToBeUpdated(Long roleToBeUpdatedId, RoleSaveRequest roleSaveRequest) {
        Role roleToBeUpdated = roleService.getRoleById(roleToBeUpdatedId);
        roleToBeUpdated.setRoleName(roleSaveRequest.getRoleName());
        return roleToBeUpdated;
    }

    public List<RoleResponse> convertToRoleResponses(List<Role> roles) {

        List<RoleResponse> roleResponses = new ArrayList<>();

        roles.forEach(role -> roleResponses.add(convertToRoleResponse(role)));
        return roleResponses;
    }
}

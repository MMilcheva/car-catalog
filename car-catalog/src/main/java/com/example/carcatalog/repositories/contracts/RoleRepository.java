package com.example.carcatalog.repositories.contracts;

import com.example.carcatalog.models.Role;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends BaseCRUDRepository<Role>  {
    List<Role> getAllRoles(Optional<String> search);
    List<Role> get();
}

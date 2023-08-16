package com.example.carcatalog.services.contracts;

import com.example.carcatalog.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> getAll(Optional<String> search);


    User getUserById(Long id);

    User getUserByUsername(String username);

    User getUserByEmail(String email);
}

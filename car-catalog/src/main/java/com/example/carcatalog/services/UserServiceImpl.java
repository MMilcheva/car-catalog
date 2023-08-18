package com.example.carcatalog.services;

import com.example.carcatalog.models.User;
import com.example.carcatalog.repositories.contracts.UserRepository;
import com.example.carcatalog.services.contracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {


    public static final String MODIFY_USER_ERROR_MESSAGE = "Only admin or user with the same id can update the information of the user.";
    private final UserRepository userRepository;


    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAll(Optional<String> search) {
        return userRepository.getAll(search);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.getById(id);
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.getByName(username);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

}

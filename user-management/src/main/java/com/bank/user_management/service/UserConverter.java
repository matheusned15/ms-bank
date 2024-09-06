package com.bank.user_management.service;

import com.bank.user_management.entities.User;
import com.bank.user_management.entities.UserDTO;
import org.springframework.stereotype.Component;

import java.util.function.Function;


@Component
public class UserConverter {

    public User convertToEntity(UserDTO userDTO) {
        return new User(
                userDTO.getId(),
                userDTO.getUsername(),
                userDTO.getEmail(),
                userDTO.getPassword_hash(),
                userDTO.isActive()
        );
    }

    public UserDTO convertToDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword_hash(),
                user.isActive()
        );
    }

    public User updateEntityFromDTO(User user, UserDTO userDTO) {
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword_hash(userDTO.getPassword_hash());
        user.setActive(userDTO.isActive());
        return user;
    }


}


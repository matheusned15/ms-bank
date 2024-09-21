package com.bank.user_management.service;

import com.bank.user_management.entities.User;
import com.bank.user_management.entities.UserResponseDTO;
import com.bank.user_management.entities.UserRequestDTO;
import org.springframework.stereotype.Component;


@Component
public class UserConverter {

    public User convertToEntity(UserRequestDTO userDTO) {
        return new User(
                userDTO.getId(),
                userDTO.getUsername(),
                userDTO.getEmail(),
                userDTO.getPassword_hash(),
                userDTO.isActive()
        );
    }

    public UserResponseDTO convertToDTO(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword_hash(),
                user.isActive()
        );
    }

    public User updateEntityFromDTO(User user, UserRequestDTO userDTO) {
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword_hash(userDTO.getPassword_hash());
        user.setActive(userDTO.isActive());
        return user;
    }


}


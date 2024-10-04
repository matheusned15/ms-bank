package com.bank.user_management.service;

import com.bank.user_management.entities.User;
import com.bank.user_management.entities.UserResponseDTO;
import com.bank.user_management.entities.UserRequestDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


@Component
public class UserConverter {

    public User convertToEntity(UserRequestDTO userDTO) {
        userDTO.setActive(true);
        return new User(
                userDTO.getUsername(),
                userDTO.getEmail(),
                userDTO.getPassword(),
                userDTO.isActive(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }

    public UserResponseDTO convertToDTO(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.isActive(),
                user.getCreated_at(),
                user.getUpdated_at()
        );
    }

    public User updateEntityFromDTO(User user, UserRequestDTO userDTO) {
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setActive(userDTO.isActive());
        return user;
    }


}


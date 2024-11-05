package com.bank.user_management.service;

import com.bank.user_management.entities.User;
import com.bank.user_management.entities.dto.UserResponseDTO;
import com.bank.user_management.entities.dto.UserRequestDTO;
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

}


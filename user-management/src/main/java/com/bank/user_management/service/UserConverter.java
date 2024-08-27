package com.bank.user_management.service;

import com.bank.user_management.entities.User;
import com.bank.user_management.entities.UserDTO;

import java.util.function.Function;

public class UserConverter {

    public static Function<UserDTO, User> dtoToEntity = dto -> new User(dto.getEmail(), dto.getUsername());

    public static Function<User, UserDTO> entityToDto = user -> new UserDTO(user.getEmail(), user.getUsername());

}

package com.danyloz.middleproject.service;

import com.danyloz.middleproject.dto.CreateUserDTO;
import com.danyloz.middleproject.dto.UserDTO;

import java.util.UUID;

public interface UserService {
    UserDTO getUser(UUID userId);
    UserDTO createUser(CreateUserDTO createUserDTO);
}

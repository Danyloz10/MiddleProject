package com.danyloz.middleproject.service.implementation;


import com.danyloz.middleproject.dto.CreateUserDTO;
import com.danyloz.middleproject.dto.UserDTO;
import com.danyloz.middleproject.entity.User;
import com.danyloz.middleproject.repository.UserRepository;
import com.danyloz.middleproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDTO getUser(UUID userId) {
        return new UserDTO(userRepository.findById(userId).orElseThrow());
    }

    @Override
    public UserDTO createUser(CreateUserDTO createUserDTO) {
        return new UserDTO(userRepository.save(User.builder()
                .username(createUserDTO.getUsername())
                .password(passwordEncoder.encode(createUserDTO.getPassword()))
                .build()));
    }
}

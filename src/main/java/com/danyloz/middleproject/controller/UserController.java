package com.danyloz.middleproject.controller;

import com.danyloz.middleproject.dto.CreateUserDTO;
import com.danyloz.middleproject.dto.UserDTO;
import com.danyloz.middleproject.entity.User;
import com.danyloz.middleproject.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/{userId}")
    @SecurityRequirement(name = "BearerAuth")
    public UserDTO getUser(@PathVariable(name = "userId") UUID userId) {
        return userService.getUser(userId);
    }

    @PostMapping()
    public UserDTO createUser(@RequestBody CreateUserDTO createUserDTO) {
        return userService.createUser(createUserDTO);
    }

    @GetMapping("/current")
    @SecurityRequirement(name = "BearerAuth")
    public UserDTO getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth.getPrincipal() instanceof User) {
            return UserDTO.builder()
                    .id(((User) auth.getPrincipal()).getId())
                    .username(((User) auth.getPrincipal()).getUsername())
                    .build();
        }
        return null;
    }

}

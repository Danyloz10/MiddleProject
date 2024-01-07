package com.danyloz.middleproject.controller;

import com.danyloz.middleproject.config.JwtHelper;
import com.danyloz.middleproject.dto.UserDTO;
import com.danyloz.middleproject.dto.UserLoginDTO;
import com.danyloz.middleproject.service.implementation.AuthServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtHelper jwtHelper;
    @Autowired
    private AuthServiceImpl userDetailsService;

    @PostMapping("/login")
    public UserDTO login(@RequestBody UserLoginDTO request) {
            this.doAuthenticate(request.getUsername(), request.getPassword());

            UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
            String token = this.jwtHelper.generateToken(userDetails);


            return UserDTO.builder()
                    .jwtToken(token)
                    .username(userDetails.getUsername()).build();
        }

    private void doAuthenticate(String email, String password) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        try {
            authenticationManager.authenticate(authentication);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(" Invalid Username or Password  !!");
        }
    }

    @ExceptionHandler(BadCredentialsException.class)
    public String exceptionHandler() {
        return "Credentials Invalid !!";
    }
}

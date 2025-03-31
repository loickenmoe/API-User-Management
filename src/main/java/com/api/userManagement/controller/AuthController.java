package com.api.userManagement.controller;

import com.api.userManagement.dto.AuthRequestDTO;
import com.api.userManagement.dto.AuthResponseDTO;
import com.api.userManagement.dto.UserDTO;
import com.api.userManagement.security.JwtTokenProvider;
import com.api.userManagement.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

/**
 * Controller for authentication operations.
 */
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Authentication API")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserService userService;

    /**
     * Authenticate user and return JWT token.
     *
     * @param loginRequest Login credentials
     * @return JWT token
     */
    @PostMapping("/login")
    @Operation(summary = "Authenticate a user", description = "Authenticates a user with email and password and returns a JWT token")
    public ResponseEntity<AuthResponseDTO> authenticateUser(@Valid @RequestBody AuthRequestDTO loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new AuthResponseDTO(jwt));
    }

    /**
     * Register a new user.
     *
     * @param userDTO User registration details
     * @return Created user
     */
    @PostMapping("/register")
    @Operation(summary = "Register a new user", description = "Creates a new user with name, email and password")
    public ResponseEntity<UserDTO> registerUser(@Valid @RequestBody UserDTO userDTO) {
        UserDTO createdUser = userService.createUser(userDTO);
        return ResponseEntity.ok(createdUser);
    }

}


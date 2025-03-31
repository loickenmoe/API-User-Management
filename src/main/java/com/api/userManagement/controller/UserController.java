package com.api.userManagement.controller;

import com.api.userManagement.dto.UserDTO;
import com.api.userManagement.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * Controller for user management operations.
 */
@RestController
@RequestMapping("/api/users")
@Tag(name = "User Management", description = "User management API")
@SecurityRequirement(name = "BearerAuth") // Obligatoire pour Swagger
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Get all users.
     *
     * @return List of users
     */
    @GetMapping
    @Operation(summary = "Get all users", description = "Returns a list of all users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    /**
     * Get user by ID.
     *
     * @param id User ID
     * @return User
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID", description = "Returns a user by their ID")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        UserDTO user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    /**
     * Update user.
     *
     * @param id User ID
     * @param userDTO User details
     * @return Updated user
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update a user", description = "Updates a user's information")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UserDTO userDTO) {
        UserDTO updatedUser = userService.updateUser(id, userDTO);
        return ResponseEntity.ok(updatedUser);
    }

    /**
     * Delete user.
     *
     * @param id User ID
     * @return No content response
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a user", description = "Deletes a user by their ID")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}


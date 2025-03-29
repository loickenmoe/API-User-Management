package com.api.userManagement.service;

import com.api.userManagement.dto.UserDTO;

import java.util.List;

/**
 * Service interface for user operations.
 */
public interface UserService {

    /**
     * Get all users.
     *
     * @return List of users
     */
    List<UserDTO> getAllUsers();

    /**
     * Get user by ID.
     *
     * @param id User ID
     * @return User
     */
    UserDTO getUserById(Long id);

    /**
     * Create a new user.
     *
     * @param userDTO User details
     * @return Created user
     */
    UserDTO createUser(UserDTO userDTO);

    /**
     * Update user.
     *
     * @param id User ID
     * @param userDTO User details
     * @return Updated user
     */
    UserDTO updateUser(Long id, UserDTO userDTO);

    /**
     * Delete user.
     *
     * @param id User ID
     */
    void deleteUser(Long id);
}


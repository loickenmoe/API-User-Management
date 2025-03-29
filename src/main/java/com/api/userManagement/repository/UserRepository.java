package com.api.userManagement.repository;

import com.api.userManagement.entity.User;
//import com.api.usermanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for User entity.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Find a user by email.
     *
     * @param email Email
     * @return Optional user
     */
    Optional<User> findByEmail(String email);

    /**
     * Check if a user exists by email.
     *
     * @param email Email
     * @return True if exists
     */
    Boolean existsByEmail(String email);
}


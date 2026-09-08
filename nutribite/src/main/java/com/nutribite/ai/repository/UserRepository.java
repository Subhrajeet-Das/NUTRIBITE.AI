package com.nutribite.ai.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nutribite.ai.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Find a user by email.
     * Used by authentication and JWT.
     */
    Optional<User> findByEmail(String email);

    /**
     * Check whether an email is already registered.
     */
    boolean existsByEmail(String email);

}
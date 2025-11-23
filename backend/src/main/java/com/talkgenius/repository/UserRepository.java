package com.talkgenius.repository;

import com.talkgenius.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * User Repository.
 *
 * <p>Provides database access methods for User entities.
 *
 * @author TalkGenius Team
 * @since 2025-01-14
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {

    /**
     * Find user by email address.
     *
     * @param email The email address
     * @return Optional containing the user if found
     */
    Optional<User> findByEmail(String email);

    /**
     * Check if a user exists with the given email.
     *
     * @param email The email address
     * @return true if user exists, false otherwise
     */
    boolean existsByEmail(String email);
}

package com.talkgenius.repository;

import com.talkgenius.model.UserPreference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * User Preference Repository.
 *
 * <p>Provides database access methods for UserPreference entities.
 *
 * @author TalkGenius Team
 * @since 2025-01-14
 */
@Repository
public interface UserPreferenceRepository extends JpaRepository<UserPreference, String> {

    /**
     * Find user preference by user ID.
     *
     * @param userId The user ID
     * @return Optional containing the user preference if found
     */
    Optional<UserPreference> findByUserId(String userId);

    /**
     * Delete user preference by user ID.
     *
     * @param userId The user ID
     */
    void deleteByUserId(String userId);
}

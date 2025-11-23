package com.talkgenius.repository;

import com.talkgenius.model.Conversation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Conversation Repository.
 *
 * <p>Provides database access methods for Conversation entities.
 *
 * @author TalkGenius Team
 * @since 2025-01-14
 */
@Repository
public interface ConversationRepository extends JpaRepository<Conversation, String> {

    /**
     * Find all conversations for a specific user.
     *
     * @param userId The user ID
     * @param pageable Pagination parameters
     * @return Page of conversations
     */
    Page<Conversation> findByUserId(String userId, Pageable pageable);

    /**
     * Find conversations for a user within a date range.
     *
     * @param userId The user ID
     * @param startDate Start date
     * @param endDate End date
     * @return List of conversations
     */
    List<Conversation> findByUserIdAndCreatedAtBetween(
        String userId,
        LocalDateTime startDate,
        LocalDateTime endDate
    );

    /**
     * Count conversations for a specific user.
     *
     * @param userId The user ID
     * @return Number of conversations
     */
    long countByUserId(String userId);
}

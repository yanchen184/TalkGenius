package com.talkgenius.repository;

import com.talkgenius.model.AIUsageLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * AI Usage Log Repository.
 *
 * <p>Provides database access methods for AIUsageLog entities.
 * Used for freemium rate limiting and usage analytics.
 *
 * @author TalkGenius Team
 * @since 2025-01-14
 */
@Repository
public interface AIUsageLogRepository extends JpaRepository<AIUsageLog, String> {

    /**
     * Count AI requests made by a user within a date range.
     * Used for freemium daily limit enforcement.
     *
     * @param userId The user ID
     * @param startDate Start of the time period
     * @param endDate End of the time period
     * @return Number of AI requests
     */
    @Query("SELECT COUNT(a) FROM AIUsageLog a WHERE a.user.id = :userId " +
           "AND a.createdAt >= :startDate AND a.createdAt < :endDate")
    long countByUserIdAndDateRange(
        @Param("userId") String userId,
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate
    );

    /**
     * Get all usage logs for a specific user.
     *
     * @param userId The user ID
     * @return List of usage logs
     */
    List<AIUsageLog> findByUserIdOrderByCreatedAtDesc(String userId);

    /**
     * Calculate total tokens used by a user.
     *
     * @param userId The user ID
     * @return Total tokens used
     */
    @Query("SELECT COALESCE(SUM(a.tokensUsed), 0) FROM AIUsageLog a WHERE a.user.id = :userId")
    long sumTokensByUserId(@Param("userId") String userId);
}

package com.talkgenius.repository;

import com.talkgenius.model.Subscription;
import com.talkgenius.model.Subscription.SubscriptionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Subscription Repository.
 *
 * <p>Provides database access methods for Subscription entities.
 *
 * @author TalkGenius Team
 * @since 2025-01-14
 */
@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, String> {

    /**
     * Find active subscription for a user.
     *
     * @param userId The user ID
     * @param status The subscription status
     * @return Optional containing the active subscription if found
     */
    Optional<Subscription> findByUserIdAndStatus(String userId, SubscriptionStatus status);

    /**
     * Find all subscriptions for a user.
     *
     * @param userId The user ID
     * @return List of subscriptions
     */
    List<Subscription> findByUserIdOrderByCreatedAtDesc(String userId);

    /**
     * Find expired subscriptions that need to be updated.
     *
     * @param now Current timestamp
     * @return List of expired subscriptions
     */
    @Query("SELECT s FROM Subscription s WHERE s.status = 'active' AND s.endDate < :now")
    List<Subscription> findExpiredSubscriptions(@Param("now") LocalDateTime now);

    /**
     * Check if a user has an active subscription.
     *
     * @param userId The user ID
     * @param now Current timestamp
     * @return true if user has active subscription
     */
    @Query("SELECT COUNT(s) > 0 FROM Subscription s WHERE s.user.id = :userId " +
           "AND s.status = 'active' AND s.endDate > :now")
    boolean hasActiveSubscription(@Param("userId") String userId, @Param("now") LocalDateTime now);
}

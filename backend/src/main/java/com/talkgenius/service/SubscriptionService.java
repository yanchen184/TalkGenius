package com.talkgenius.service;

import com.talkgenius.model.Subscription;
import com.talkgenius.model.Subscription.SubscriptionStatus;
import com.talkgenius.model.User;
import com.talkgenius.repository.SubscriptionRepository;
import com.talkgenius.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Subscription Service.
 *
 * <p>Manages premium subscriptions and freemium tier logic.
 *
 * @author TalkGenius Team
 * @since 2025-01-14
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;

    /**
     * Check if user has an active premium subscription.
     *
     * @param userId User ID
     * @return true if user has active subscription
     */
    @Transactional(readOnly = true)
    public boolean hasActivePremiumSubscription(String userId) {
        return subscriptionRepository.hasActiveSubscription(userId, LocalDateTime.now());
    }

    /**
     * Get user's active subscription.
     *
     * @param userId User ID
     * @return Active subscription or null
     */
    @Transactional(readOnly = true)
    public Subscription getActiveSubscription(String userId) {
        return subscriptionRepository.findByUserIdAndStatus(userId, SubscriptionStatus.active)
                .orElse(null);
    }

    /**
     * Get all subscriptions for a user.
     *
     * @param userId User ID
     * @return List of subscriptions
     */
    @Transactional(readOnly = true)
    public List<Subscription> getUserSubscriptions(String userId) {
        return subscriptionRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    /**
     * Create a new subscription (called after successful payment).
     *
     * @param userId User ID
     * @param planType Plan type
     * @param paymentProvider Payment provider
     * @param transactionId Transaction ID
     * @return Created subscription
     */
    @Transactional
    public Subscription createSubscription(
            String userId,
            Subscription.PlanType planType,
            String paymentProvider,
            String transactionId
    ) {
        log.info("Creating subscription for user: {}, plan: {}", userId, planType);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Calculate end date based on plan type
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = planType == Subscription.PlanType.monthly
                ? startDate.plusMonths(1)
                : startDate.plusYears(1);

        Subscription subscription = Subscription.builder()
                .user(user)
                .planType(planType)
                .status(SubscriptionStatus.active)
                .startDate(startDate)
                .endDate(endDate)
                .paymentProvider(paymentProvider)
                .transactionId(transactionId)
                .build();

        subscription = subscriptionRepository.save(subscription);

        // Update user premium status
        user.setIsPremium(true);
        userRepository.save(user);

        log.info("Subscription created successfully: {}", subscription.getId());
        return subscription;
    }

    /**
     * Cancel a subscription.
     *
     * @param subscriptionId Subscription ID
     */
    @Transactional
    public void cancelSubscription(String subscriptionId) {
        Subscription subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new RuntimeException("Subscription not found"));

        subscription.setStatus(SubscriptionStatus.canceled);
        subscriptionRepository.save(subscription);

        // Update user premium status if no other active subscriptions
        String userId = subscription.getUser().getId();
        if (!hasActivePremiumSubscription(userId)) {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            user.setIsPremium(false);
            userRepository.save(user);
        }

        log.info("Subscription canceled: {}", subscriptionId);
    }

    /**
     * Update expired subscriptions (scheduled task).
     */
    @Transactional
    public void updateExpiredSubscriptions() {
        List<Subscription> expiredSubscriptions = subscriptionRepository.findExpiredSubscriptions(LocalDateTime.now());

        for (Subscription subscription : expiredSubscriptions) {
            subscription.setStatus(SubscriptionStatus.expired);
            subscriptionRepository.save(subscription);

            // Update user premium status
            String userId = subscription.getUser().getId();
            if (!hasActivePremiumSubscription(userId)) {
                User user = userRepository.findById(userId)
                        .orElseThrow(() -> new RuntimeException("User not found"));
                user.setIsPremium(false);
                userRepository.save(user);
            }

            log.info("Subscription expired: {}", subscription.getId());
        }
    }
}

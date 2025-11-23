package com.talkgenius.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Subscription Entity.
 *
 * <p>Tracks user premium subscriptions and billing information.
 *
 * @author TalkGenius Team
 * @since 2025-01-14
 */
@Entity
@Table(name = "subscriptions", indexes = {
    @Index(name = "idx_user_id", columnList = "user_id"),
    @Index(name = "idx_status", columnList = "status")
})
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Subscription {

    /**
     * Unique subscription ID (UUID format).
     */
    @Id
    @Column(length = 36)
    private String id;

    /**
     * Reference to the user who owns this subscription.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Subscription plan type.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "ENUM('monthly', 'yearly')")
    @Builder.Default
    private PlanType planType = PlanType.monthly;

    /**
     * Subscription status.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "ENUM('active', 'canceled', 'expired', 'pending')")
    @Builder.Default
    private SubscriptionStatus status = SubscriptionStatus.pending;

    /**
     * Subscription start date.
     */
    @Column(name = "start_date")
    private LocalDateTime startDate;

    /**
     * Subscription end date.
     */
    @Column(name = "end_date")
    private LocalDateTime endDate;

    /**
     * Payment amount.
     */
    @Column(precision = 10, scale = 2)
    private BigDecimal price;

    /**
     * Payment provider (e.g., Stripe, PayPal).
     */
    @Column(name = "payment_provider", length = 50)
    private String paymentProvider;

    /**
     * External transaction ID from payment provider.
     */
    @Column(name = "transaction_id", length = 255)
    private String transactionId;

    /**
     * Timestamp when the subscription was created.
     */
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * Timestamp when the subscription was last updated.
     */
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * Subscription plan type enum.
     */
    public enum PlanType {
        monthly,
        yearly
    }

    /**
     * Subscription status enum.
     */
    public enum SubscriptionStatus {
        active,
        canceled,
        expired,
        pending
    }

    /**
     * Pre-persist callback to generate UUID.
     */
    @PrePersist
    public void prePersist() {
        if (this.id == null) {
            this.id = java.util.UUID.randomUUID().toString();
        }
        if (this.planType == null) {
            this.planType = PlanType.monthly;
        }
        if (this.status == null) {
            this.status = SubscriptionStatus.pending;
        }
    }

    /**
     * Check if the subscription is currently active.
     *
     * @return true if active and not expired
     */
    public boolean isActive() {
        return status == SubscriptionStatus.active &&
               endDate != null &&
               endDate.isAfter(LocalDateTime.now());
    }
}

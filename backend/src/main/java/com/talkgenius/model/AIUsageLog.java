package com.talkgenius.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * AI Usage Log Entity.
 *
 * <p>Tracks AI API usage for freemium rate limiting and analytics.
 *
 * @author TalkGenius Team
 * @since 2025-01-14
 */
@Entity
@Table(name = "ai_usage_log", indexes = {
    @Index(name = "idx_user_created", columnList = "user_id, created_at")
})
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AIUsageLog {

    /**
     * Unique log ID (UUID format).
     */
    @Id
    @Column(length = 36)
    private String id;

    /**
     * Reference to the user who made the AI request.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Type of AI operation (e.g., generate_reply, analyze_message).
     */
    @Column(name = "operation_type", length = 50)
    private String operationType;

    /**
     * Number of tokens used in the request.
     */
    @Column(name = "tokens_used")
    private Integer tokensUsed;

    /**
     * OpenAI model used for the request.
     */
    @Column(name = "model_used", length = 50)
    private String modelUsed;

    /**
     * Whether the request was successful.
     */
    @Column(name = "success", nullable = false)
    @Builder.Default
    private Boolean success = true;

    /**
     * Error message if the request failed.
     */
    @Column(name = "error_message", columnDefinition = "TEXT")
    private String errorMessage;

    /**
     * Timestamp when the AI request was made.
     */
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * Pre-persist callback to generate UUID.
     */
    @PrePersist
    public void prePersist() {
        if (this.id == null) {
            this.id = java.util.UUID.randomUUID().toString();
        }
        if (this.success == null) {
            this.success = true;
        }
    }
}

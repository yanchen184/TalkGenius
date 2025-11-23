package com.talkgenius.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * Conversation Entity.
 *
 * <p>Represents a conversation thread between the user and their dating match.
 * Each conversation can have multiple messages.
 *
 * @author TalkGenius Team
 * @since 2025-01-14
 */
@Entity
@Table(name = "conversation_history", indexes = {
    @Index(name = "idx_user_id", columnList = "user_id"),
    @Index(name = "idx_created_at", columnList = "created_at")
})
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Conversation {

    /**
     * Unique conversation ID (UUID format).
     */
    @Id
    @Column(length = 36)
    private String id;

    /**
     * Reference to the user who owns this conversation.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Name of the match/conversation partner.
     */
    @NotBlank(message = "Match name is required")
    @Column(name = "match_name", nullable = false, length = 100)
    private String matchName;

    /**
     * App where the conversation is taking place (e.g., Tinder, Bumble).
     */
    @Column(name = "source_app", length = 50)
    private String sourceApp;

    /**
     * Original received message from the match.
     */
    @Column(name = "received_message", columnDefinition = "TEXT")
    private String receivedMessage;

    /**
     * AI-generated reply suggestion.
     */
    @Column(name = "generated_reply", columnDefinition = "TEXT")
    private String generatedReply;

    /**
     * Tone style used for the generated reply.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "tone_style", columnDefinition = "ENUM('Humorous', 'High_EQ', 'Gentle', 'Cute', 'Romantic', 'Professional', 'Direct', 'Flirty')")
    private ToneStyle toneStyle;

    /**
     * Timestamp when the conversation was created.
     */
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * Tone style enum (8 styles).
     */
    public enum ToneStyle {
        Humorous,
        High_EQ,
        Gentle,
        Cute,
        Romantic,
        Professional,
        Direct,
        Flirty
    }

    /**
     * Pre-persist callback to generate UUID.
     */
    @PrePersist
    public void prePersist() {
        if (this.id == null) {
            this.id = java.util.UUID.randomUUID().toString();
        }
    }
}

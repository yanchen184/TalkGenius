package com.talkgenius.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * User Preference Entity.
 *
 * <p>Stores user preferences for AI tone styles and other settings.
 *
 * @author TalkGenius Team
 * @since 2025-01-14
 */
@Entity
@Table(name = "user_preferences", uniqueConstraints = {
    @UniqueConstraint(name = "uk_user_id", columnNames = "user_id")
})
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPreference {

    /**
     * Unique preference ID (UUID format).
     */
    @Id
    @Column(length = 36)
    private String id;

    /**
     * Reference to the user who owns these preferences.
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Default tone style preference.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "default_tone", columnDefinition = "ENUM('Humorous', 'High_EQ', 'Gentle', 'Cute', 'Romantic', 'Professional', 'Direct', 'Flirty')")
    @Builder.Default
    private Conversation.ToneStyle defaultTone = Conversation.ToneStyle.High_EQ;

    /**
     * Flirtiness level (1-10).
     */
    @Column(name = "flirtiness_level")
    @Builder.Default
    private Integer flirtinessLevel = 5;

    /**
     * Emoji usage level (0-10).
     */
    @Column(name = "emoji_level")
    @Builder.Default
    private Integer emojiLevel = 5;

    /**
     * Whether to enable emotional coaching.
     */
    @Column(name = "enable_coaching", nullable = false)
    @Builder.Default
    private Boolean enableCoaching = true;

    /**
     * Timestamp when preferences were created.
     */
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * Timestamp when preferences were last updated.
     */
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * Pre-persist callback to generate UUID.
     */
    @PrePersist
    public void prePersist() {
        if (this.id == null) {
            this.id = java.util.UUID.randomUUID().toString();
        }
        if (this.defaultTone == null) {
            this.defaultTone = Conversation.ToneStyle.High_EQ;
        }
        if (this.flirtinessLevel == null) {
            this.flirtinessLevel = 5;
        }
        if (this.emojiLevel == null) {
            this.emojiLevel = 5;
        }
        if (this.enableCoaching == null) {
            this.enableCoaching = true;
        }
    }
}

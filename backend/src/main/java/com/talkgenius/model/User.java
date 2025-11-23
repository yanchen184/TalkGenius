package com.talkgenius.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * User Entity.
 *
 * <p>Represents a user in the TalkGenius application.
 * Stores user credentials, profile information, and premium status.
 *
 * @author TalkGenius Team
 * @since 2025-01-14
 */
@Entity
@Table(name = "users", indexes = {
    @Index(name = "idx_email", columnList = "email")
})
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    /**
     * Unique user ID (UUID format).
     */
    @Id
    @Column(length = 36)
    private String id;

    /**
     * User email address (unique, required).
     */
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    @Column(unique = true, nullable = false)
    private String email;

    /**
     * Bcrypt hashed password (required).
     */
    @NotBlank(message = "Password is required")
    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    /**
     * User display name.
     */
    @Size(max = 100, message = "Username must be less than 100 characters")
    @Column(length = 100)
    private String username;

    /**
     * User age.
     */
    private Integer age;

    /**
     * User gender.
     */
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('male', 'female', 'other', 'prefer_not_to_say')")
    private Gender gender;

    /**
     * User relationship status.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "relationship_status", columnDefinition = "ENUM('single', 'dating', 'in_relationship', 'married')")
    private RelationshipStatus relationshipStatus;

    /**
     * Premium subscription status.
     */
    @Column(name = "is_premium", nullable = false)
    @Builder.Default
    private Boolean isPremium = false;

    /**
     * Timestamp when the user was created.
     */
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * Timestamp when the user was last updated.
     */
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * Gender enum.
     */
    public enum Gender {
        male,
        female,
        other,
        prefer_not_to_say
    }

    /**
     * Relationship status enum.
     */
    public enum RelationshipStatus {
        single,
        dating,
        in_relationship,
        married
    }

    /**
     * Pre-persist callback to generate UUID.
     */
    @PrePersist
    public void prePersist() {
        if (this.id == null) {
            this.id = java.util.UUID.randomUUID().toString();
        }
        if (this.isPremium == null) {
            this.isPremium = false;
        }
    }
}

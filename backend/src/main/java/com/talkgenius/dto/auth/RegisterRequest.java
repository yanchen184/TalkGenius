package com.talkgenius.dto.auth;

import com.talkgenius.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Registration Request DTO.
 *
 * <p>Request body for user registration endpoint.
 *
 * @author TalkGenius Team
 * @since 2025-01-14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters")
    private String password;

    @Size(max = 100, message = "Username must be less than 100 characters")
    private String username;

    private Integer age;

    private User.Gender gender;

    private User.RelationshipStatus relationshipStatus;
}

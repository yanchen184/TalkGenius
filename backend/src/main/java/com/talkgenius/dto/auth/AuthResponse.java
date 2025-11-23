package com.talkgenius.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Authentication Response DTO.
 *
 * <p>Response body for login and registration endpoints.
 * Contains JWT tokens and user information.
 *
 * @author TalkGenius Team
 * @since 2025-01-14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponse {

    private String accessToken;
    private String refreshToken;
    private String tokenType = "Bearer";
    private UserInfo user;

    /**
     * Nested user information class.
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UserInfo {
        private String id;
        private String email;
        private String username;
        private boolean isPremium;
    }
}

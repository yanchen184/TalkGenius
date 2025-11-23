package com.talkgenius.service;

import com.talkgenius.dto.auth.AuthResponse;
import com.talkgenius.dto.auth.LoginRequest;
import com.talkgenius.dto.auth.RegisterRequest;
import com.talkgenius.exception.EmailAlreadyExistsException;
import com.talkgenius.model.User;
import com.talkgenius.model.UserPreference;
import com.talkgenius.repository.UserPreferenceRepository;
import com.talkgenius.repository.UserRepository;
import com.talkgenius.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Authentication Service.
 *
 * <p>Handles user registration, login, and JWT token generation.
 *
 * @author TalkGenius Team
 * @since 2025-01-14
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final UserPreferenceRepository userPreferenceRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    /**
     * Register a new user.
     *
     * @param request Registration request
     * @return Authentication response with tokens
     * @throws EmailAlreadyExistsException if email already exists
     */
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        log.info("Registering new user with email: {}", request.getEmail());

        // Check if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists: " + request.getEmail());
        }

        // Create new user
        User user = User.builder()
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .username(request.getUsername())
                .age(request.getAge())
                .gender(request.getGender())
                .relationshipStatus(request.getRelationshipStatus())
                .isPremium(false)
                .build();

        user = userRepository.save(user);
        log.info("User registered successfully with ID: {}", user.getId());

        // Create default user preferences
        UserPreference preference = UserPreference.builder()
                .user(user)
                .build();
        userPreferenceRepository.save(preference);
        log.debug("Created default preferences for user: {}", user.getId());

        // Generate tokens
        String accessToken = jwtTokenProvider.generateAccessToken(user.getId());
        String refreshToken = jwtTokenProvider.generateRefreshToken(user.getId());

        return buildAuthResponse(user, accessToken, refreshToken);
    }

    /**
     * Login existing user.
     *
     * @param request Login request
     * @return Authentication response with tokens
     */
    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest request) {
        log.info("User login attempt: {}", request.getEmail());

        // Authenticate user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // Get user from database
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        log.info("User logged in successfully: {}", user.getId());

        // Generate tokens
        String accessToken = jwtTokenProvider.generateAccessToken(user.getId());
        String refreshToken = jwtTokenProvider.generateRefreshToken(user.getId());

        return buildAuthResponse(user, accessToken, refreshToken);
    }

    /**
     * Refresh access token.
     *
     * @param refreshToken Refresh token
     * @return New authentication response
     */
    @Transactional(readOnly = true)
    public AuthResponse refreshToken(String refreshToken) {
        // Validate refresh token
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new RuntimeException("Invalid refresh token");
        }

        // Get user ID from token
        String userId = jwtTokenProvider.getUserIdFromToken(refreshToken);

        // Get user from database
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Generate new access token
        String newAccessToken = jwtTokenProvider.generateAccessToken(user.getId());

        return buildAuthResponse(user, newAccessToken, refreshToken);
    }

    /**
     * Build authentication response.
     *
     * @param user User entity
     * @param accessToken Access token
     * @param refreshToken Refresh token
     * @return Authentication response
     */
    private AuthResponse buildAuthResponse(User user, String accessToken, String refreshToken) {
        AuthResponse.UserInfo userInfo = AuthResponse.UserInfo.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .isPremium(user.getIsPremium())
                .build();

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .user(userInfo)
                .build();
    }
}

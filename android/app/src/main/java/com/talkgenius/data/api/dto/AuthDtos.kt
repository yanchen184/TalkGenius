package com.talkgenius.data.api.dto

import com.google.gson.annotations.SerializedName

/**
 * DTOs for authentication API endpoints.
 */

data class RegisterRequest(
    val email: String,
    val password: String,
    val displayName: String? = null,
    val gender: String? = null,
    val age: Int? = null
)

data class LoginRequest(
    val email: String,
    val password: String
)

data class AuthResponse(
    val userId: String,
    val email: String,
    val displayName: String?,
    val accessToken: String,
    val refreshToken: String,
    @SerializedName("expiresIn") val expiresIn: Long,
    val isPremium: Boolean
)

data class RefreshTokenRequest(
    val refreshToken: String
)

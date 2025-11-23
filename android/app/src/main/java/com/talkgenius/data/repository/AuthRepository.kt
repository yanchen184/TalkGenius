package com.talkgenius.data.repository

import com.talkgenius.data.api.AuthApiService
import com.talkgenius.data.api.dto.AuthResponse
import com.talkgenius.data.api.dto.LoginRequest
import com.talkgenius.data.api.dto.RefreshTokenRequest
import com.talkgenius.data.api.dto.RegisterRequest
import com.talkgenius.data.local.TokenManager
import com.talkgenius.data.local.dao.UserDao
import com.talkgenius.data.local.entity.UserEntity
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository for authentication operations.
 * Handles API calls and local data persistence.
 */
@Singleton
class AuthRepository @Inject constructor(
    private val authApiService: AuthApiService,
    private val tokenManager: TokenManager,
    private val userDao: UserDao
) {

    /**
     * Register a new user.
     */
    suspend fun register(
        email: String,
        password: String,
        displayName: String? = null,
        gender: String? = null,
        age: Int? = null
    ): Result<AuthResponse> {
        return try {
            val request = RegisterRequest(email, password, displayName, gender, age)
            val response = authApiService.register(request)

            if (response.isSuccessful && response.body() != null) {
                val authResponse = response.body()!!
                saveAuthData(authResponse)
                Result.success(authResponse)
            } else {
                Result.failure(Exception("Registration failed: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Login existing user.
     */
    suspend fun login(email: String, password: String): Result<AuthResponse> {
        return try {
            val request = LoginRequest(email, password)
            val response = authApiService.login(request)

            if (response.isSuccessful && response.body() != null) {
                val authResponse = response.body()!!
                saveAuthData(authResponse)
                Result.success(authResponse)
            } else {
                Result.failure(Exception("Login failed: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Refresh access token using refresh token.
     */
    suspend fun refreshToken(): Result<AuthResponse> {
        return try {
            val refreshToken = tokenManager.getRefreshToken().first()
                ?: return Result.failure(Exception("No refresh token available"))

            val request = RefreshTokenRequest(refreshToken)
            val response = authApiService.refreshToken(request)

            if (response.isSuccessful && response.body() != null) {
                val authResponse = response.body()!!
                saveAuthData(authResponse)
                Result.success(authResponse)
            } else {
                Result.failure(Exception("Token refresh failed: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Logout user by clearing local data.
     */
    suspend fun logout() {
        try {
            authApiService.logout()
        } catch (e: Exception) {
            // Ignore API errors during logout
        } finally {
            tokenManager.clearTokens()
            userDao.deleteAllUsers()
        }
    }

    /**
     * Check if user is authenticated.
     */
    suspend fun isAuthenticated(): Boolean {
        return tokenManager.getAccessToken().first() != null
    }

    /**
     * Get current user ID.
     */
    suspend fun getCurrentUserId(): String? {
        return tokenManager.getUserId().first()
    }

    /**
     * Save authentication data locally.
     */
    private suspend fun saveAuthData(authResponse: AuthResponse) {
        // Save tokens
        tokenManager.saveAccessToken(authResponse.accessToken)
        tokenManager.saveRefreshToken(authResponse.refreshToken)
        tokenManager.saveUserId(authResponse.userId)

        // Save user entity
        val userEntity = UserEntity(
            userId = authResponse.userId,
            email = authResponse.email,
            displayName = authResponse.displayName,
            isPremium = authResponse.isPremium
        )
        userDao.insertUser(userEntity)
    }
}

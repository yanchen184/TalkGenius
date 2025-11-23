package com.talkgenius.data.api

import com.talkgenius.data.api.dto.AuthResponse
import com.talkgenius.data.api.dto.LoginRequest
import com.talkgenius.data.api.dto.RefreshTokenRequest
import com.talkgenius.data.api.dto.RegisterRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Retrofit API service for authentication endpoints.
 */
interface AuthApiService {

    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<AuthResponse>

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<AuthResponse>

    @POST("auth/refresh")
    suspend fun refreshToken(@Body request: RefreshTokenRequest): Response<AuthResponse>

    @POST("auth/logout")
    suspend fun logout(): Response<Unit>
}

package com.talkgenius.data.api

import com.talkgenius.data.local.TokenManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

/**
 * OkHttp interceptor that adds JWT access token to API requests.
 */
class AuthInterceptor @Inject constructor(
    private val tokenManager: TokenManager
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        // Skip token for auth endpoints
        if (request.url.encodedPath.contains("/auth/")) {
            return chain.proceed(request)
        }

        // Add access token to header
        val accessToken = runBlocking {
            tokenManager.getAccessToken().first()
        }

        val newRequest = if (accessToken != null) {
            request.newBuilder()
                .addHeader("Authorization", "Bearer $accessToken")
                .build()
        } else {
            request
        }

        return chain.proceed(newRequest)
    }
}

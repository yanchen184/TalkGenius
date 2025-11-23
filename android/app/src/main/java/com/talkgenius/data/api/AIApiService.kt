package com.talkgenius.data.api

import com.talkgenius.data.api.dto.GenerateReplyRequest
import com.talkgenius.data.api.dto.GenerateReplyResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Retrofit API service for AI reply generation endpoints.
 */
interface AIApiService {

    @POST("ai/generate-reply")
    suspend fun generateReply(@Body request: GenerateReplyRequest): Response<GenerateReplyResponse>
}

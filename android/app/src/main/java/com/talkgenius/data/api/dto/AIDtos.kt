package com.talkgenius.data.api.dto

import com.talkgenius.data.model.ToneStyle

/**
 * DTOs for AI reply generation API endpoints.
 */

data class GenerateReplyRequest(
    val receivedMessage: String,
    val toneStyle: ToneStyle,
    val conversationContext: String? = null
)

data class GenerateReplyResponse(
    val conversationId: String,
    val generatedReply: String,
    val toneStyle: ToneStyle,
    val timestamp: String
)

package com.talkgenius.data.repository

import com.talkgenius.data.api.AIApiService
import com.talkgenius.data.api.dto.GenerateReplyRequest
import com.talkgenius.data.api.dto.GenerateReplyResponse
import com.talkgenius.data.local.TokenManager
import com.talkgenius.data.local.dao.ConversationDao
import com.talkgenius.data.local.entity.ConversationEntity
import com.talkgenius.data.model.ToneStyle
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository for AI reply generation operations.
 * Handles API calls and local conversation history persistence.
 */
@Singleton
class AIRepository @Inject constructor(
    private val aiApiService: AIApiService,
    private val conversationDao: ConversationDao,
    private val tokenManager: TokenManager
) {

    /**
     * Generate AI reply for a received message.
     */
    suspend fun generateReply(
        receivedMessage: String,
        toneStyle: ToneStyle,
        conversationContext: String? = null
    ): Result<GenerateReplyResponse> {
        return try {
            val request = GenerateReplyRequest(
                receivedMessage = receivedMessage,
                toneStyle = toneStyle,
                conversationContext = conversationContext
            )

            val response = aiApiService.generateReply(request)

            if (response.isSuccessful && response.body() != null) {
                val replyResponse = response.body()!!
                saveConversationLocally(replyResponse, receivedMessage, conversationContext)
                Result.success(replyResponse)
            } else {
                Result.failure(Exception("AI reply generation failed: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Get recent conversation history for context.
     */
    suspend fun getRecentConversations(limit: Int = 5): List<ConversationEntity> {
        val userId = tokenManager.getUserId().first() ?: return emptyList()
        return conversationDao.getRecentConversations(userId, limit)
    }

    /**
     * Build conversation context from recent history.
     */
    suspend fun buildConversationContext(limit: Int = 3): String? {
        val recent = getRecentConversations(limit)
        if (recent.isEmpty()) return null

        return recent.reversed().joinToString("\n") { conv ->
            "Received: ${conv.receivedMessage}\nReplied: ${conv.generatedReply}"
        }
    }

    /**
     * Save conversation to local database.
     */
    private suspend fun saveConversationLocally(
        response: GenerateReplyResponse,
        receivedMessage: String,
        context: String?
    ) {
        val userId = tokenManager.getUserId().first() ?: return

        val conversation = ConversationEntity(
            conversationId = response.conversationId,
            userId = userId,
            receivedMessage = receivedMessage,
            generatedReply = response.generatedReply,
            toneStyle = response.toneStyle,
            contextSnapshot = context
        )

        conversationDao.insertConversation(conversation)
    }

    /**
     * Delete all conversations for current user.
     */
    suspend fun clearConversationHistory() {
        val userId = tokenManager.getUserId().first() ?: return
        conversationDao.deleteUserConversations(userId)
    }
}

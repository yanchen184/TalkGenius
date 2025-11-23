package com.talkgenius.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.talkgenius.data.model.ToneStyle
import com.talkgenius.data.repository.AIRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for keyboard operations and AI reply generation.
 */
@HiltViewModel
class KeyboardViewModel @Inject constructor(
    private val aiRepository: AIRepository
) : ViewModel() {

    private val _aiReplyState = MutableStateFlow(AIReplyState())
    val aiReplyState: StateFlow<AIReplyState> = _aiReplyState.asStateFlow()

    /**
     * Generate AI reply for a received message.
     */
    fun generateReply(
        receivedMessage: String,
        toneStyle: ToneStyle,
        conversationContext: String? = null
    ) {
        viewModelScope.launch {
            _aiReplyState.value = AIReplyState(isLoading = true)

            val result = aiRepository.generateReply(
                receivedMessage = receivedMessage,
                toneStyle = toneStyle,
                conversationContext = conversationContext
            )

            result.fold(
                onSuccess = { response ->
                    _aiReplyState.value = AIReplyState(
                        reply = response.generatedReply,
                        conversationId = response.conversationId,
                        isLoading = false
                    )
                },
                onFailure = { exception ->
                    _aiReplyState.value = AIReplyState(
                        error = exception.message ?: "Unknown error",
                        isLoading = false
                    )
                }
            )
        }
    }

    /**
     * Build conversation context from recent history.
     */
    suspend fun buildConversationContext(limit: Int = 3): String? {
        return aiRepository.buildConversationContext(limit)
    }

    /**
     * Clear conversation history.
     */
    fun clearHistory() {
        viewModelScope.launch {
            aiRepository.clearConversationHistory()
        }
    }

    /**
     * Reset AI reply state.
     */
    fun resetState() {
        _aiReplyState.value = AIReplyState()
    }
}

/**
 * State for AI reply generation.
 */
data class AIReplyState(
    val reply: String? = null,
    val conversationId: String? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

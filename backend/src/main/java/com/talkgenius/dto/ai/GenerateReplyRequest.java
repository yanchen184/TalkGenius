package com.talkgenius.dto.ai;

import com.talkgenius.model.Conversation;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generate Reply Request DTO.
 *
 * <p>Request body for AI reply generation endpoint.
 *
 * @author TalkGenius Team
 * @since 2025-01-14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GenerateReplyRequest {

    @NotBlank(message = "Received message is required")
    private String receivedMessage;

    private String matchName;

    private String sourceApp;

    private Conversation.ToneStyle toneStyle;

    private String conversationContext; // Optional: previous messages for context
}

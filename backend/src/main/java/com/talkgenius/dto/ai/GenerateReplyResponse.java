package com.talkgenius.dto.ai;

import com.talkgenius.model.Conversation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generate Reply Response DTO.
 *
 * <p>Response body for AI reply generation endpoint.
 *
 * @author TalkGenius Team
 * @since 2025-01-14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GenerateReplyResponse {

    private String conversationId;
    private String generatedReply;
    private Conversation.ToneStyle toneStyle;
    private Integer tokensUsed;
    private String emotionalAnalysis; // Optional emotional analysis
    private String coachingTip; // Optional coaching tip
}

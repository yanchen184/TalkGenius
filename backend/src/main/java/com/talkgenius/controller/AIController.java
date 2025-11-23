package com.talkgenius.controller;

import com.talkgenius.dto.ai.GenerateReplyRequest;
import com.talkgenius.dto.ai.GenerateReplyResponse;
import com.talkgenius.security.UserPrincipal;
import com.talkgenius.service.AIReplyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/**
 * AI Controller.
 *
 * <p>REST endpoints for AI-powered reply generation.
 *
 * @author TalkGenius Team
 * @since 2025-01-14
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/ai")
@RequiredArgsConstructor
public class AIController {

    private final AIReplyService aiReplyService;

    /**
     * Generate AI reply based on received message.
     *
     * POST /api/v1/ai/generate-reply
     *
     * @param userPrincipal Authenticated user
     * @param request Generation request
     * @return Generated reply response
     */
    @PostMapping("/generate-reply")
    public ResponseEntity<GenerateReplyResponse> generateReply(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @Valid @RequestBody GenerateReplyRequest request
    ) {
        log.info("Generate reply request from user: {}", userPrincipal.getId());
        GenerateReplyResponse response = aiReplyService.generateReply(userPrincipal.getId(), request);
        return ResponseEntity.ok(response);
    }
}

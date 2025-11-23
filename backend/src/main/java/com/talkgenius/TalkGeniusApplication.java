package com.talkgenius;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * TalkGenius AI Dating Assistant - Backend Application
 *
 * <p>This is the main entry point for the TalkGenius backend service.
 * It provides AI-powered reply generation for dating app conversations.
 *
 * <p>Key Features:
 * <ul>
 *   <li>8 AI tone styles (Humorous, High EQ, Gentle, Cute, Romantic, Professional, Direct, Flirty)</li>
 *   <li>OpenAI GPT-4 integration via Spring AI</li>
 *   <li>JWT authentication</li>
 *   <li>Redis caching for AI responses</li>
 *   <li>Freemium model (10 requests/day for free users)</li>
 * </ul>
 *
 * @author TalkGenius Team
 * @version 0.0.1
 * @since 2025-01-14
 */
@SpringBootApplication
@EnableJpaAuditing
public class TalkGeniusApplication {

    public static void main(String[] args) {
        SpringApplication.run(TalkGeniusApplication.class, args);
    }

}

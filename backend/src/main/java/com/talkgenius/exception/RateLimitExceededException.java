package com.talkgenius.exception;

/**
 * Rate Limit Exceeded Exception.
 *
 * <p>Thrown when a free user exceeds their daily API request limit.
 *
 * @author TalkGenius Team
 * @since 2025-01-14
 */
public class RateLimitExceededException extends RuntimeException {

    public RateLimitExceededException(String message) {
        super(message);
    }

    public RateLimitExceededException(String message, Throwable cause) {
        super(message, cause);
    }
}

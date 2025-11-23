package com.talkgenius.exception;

/**
 * Email Already Exists Exception.
 *
 * <p>Thrown when attempting to register with an email that already exists.
 *
 * @author TalkGenius Team
 * @since 2025-01-14
 */
public class EmailAlreadyExistsException extends RuntimeException {

    public EmailAlreadyExistsException(String message) {
        super(message);
    }

    public EmailAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}

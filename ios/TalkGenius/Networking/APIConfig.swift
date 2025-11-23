//
//  APIConfig.swift
//  TalkGenius
//
//  API configuration and constants.
//

import Foundation

struct APIConfig {
    // Base URL - change for production
    #if DEBUG
    static let baseURL = "http://localhost:8080/api/v1"
    #else
    static let baseURL = "https://api.talkgenius.com/api/v1"
    #endif

    // Endpoints
    struct Endpoints {
        // Auth
        static let register = "/auth/register"
        static let login = "/auth/login"
        static let refreshToken = "/auth/refresh"
        static let logout = "/auth/logout"

        // AI
        static let generateReply = "/ai/generate-reply"
    }

    // Headers
    struct Headers {
        static let contentType = "Content-Type"
        static let authorization = "Authorization"
        static let applicationJSON = "application/json"
    }

    // Timeouts
    static let requestTimeout: TimeInterval = 30
}

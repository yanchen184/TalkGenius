//
//  AuthModels.swift
//  TalkGenius
//
//  Authentication request/response models.
//

import Foundation

// MARK: - Request Models

struct RegisterRequest: Codable {
    let email: String
    let password: String
    let displayName: String?
    let gender: String?
    let age: Int?
}

struct LoginRequest: Codable {
    let email: String
    let password: String
}

struct RefreshTokenRequest: Codable {
    let refreshToken: String
}

// MARK: - Response Models

struct AuthResponse: Codable {
    let userId: String
    let email: String
    let displayName: String?
    let accessToken: String
    let refreshToken: String
    let expiresIn: Int
    let isPremium: Bool
}

// MARK: - User Model

struct User: Codable {
    let id: String
    let email: String
    let displayName: String?
    let isPremium: Bool
    let createdAt: Date?

    enum CodingKeys: String, CodingKey {
        case id
        case email
        case displayName
        case isPremium
        case createdAt
    }
}

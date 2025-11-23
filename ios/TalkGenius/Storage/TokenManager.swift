//
//  TokenManager.swift
//  TalkGenius
//
//  Manages JWT tokens using Keychain.
//

import Foundation
import KeychainAccess

class TokenManager {

    static let shared = TokenManager()

    private let keychain = Keychain(service: "com.talkgenius.app")

    private enum Keys {
        static let accessToken = "access_token"
        static let refreshToken = "refresh_token"
        static let userId = "user_id"
    }

    private init() {}

    // MARK: - Access Token

    func saveAccessToken(_ token: String) throws {
        try keychain.set(token, key: Keys.accessToken)
    }

    func getAccessToken() -> String? {
        return try? keychain.get(Keys.accessToken)
    }

    // MARK: - Refresh Token

    func saveRefreshToken(_ token: String) throws {
        try keychain.set(token, key: Keys.refreshToken)
    }

    func getRefreshToken() -> String? {
        return try? keychain.get(Keys.refreshToken)
    }

    // MARK: - User ID

    func saveUserId(_ userId: String) throws {
        try keychain.set(userId, key: Keys.userId)
    }

    func getUserId() -> String? {
        return try? keychain.get(Keys.userId)
    }

    // MARK: - Clear All

    func clearAll() {
        try? keychain.remove(Keys.accessToken)
        try? keychain.remove(Keys.refreshToken)
        try? keychain.remove(Keys.userId)
    }

    // MARK: - Check Authentication

    func isAuthenticated() -> Bool {
        return getAccessToken() != nil
    }
}

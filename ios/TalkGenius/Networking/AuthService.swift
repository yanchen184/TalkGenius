//
//  AuthService.swift
//  TalkGenius
//
//  Authentication API service.
//

import Foundation

class AuthService {

    static let shared = AuthService()
    private let networkManager = NetworkManager.shared

    private init() {}

    // MARK: - Register

    func register(
        email: String,
        password: String,
        displayName: String? = nil,
        gender: String? = nil,
        age: Int? = nil,
        completion: @escaping (Result<AuthResponse, Error>) -> Void
    ) {
        let request = RegisterRequest(
            email: email,
            password: password,
            displayName: displayName,
            gender: gender,
            age: age
        )

        networkManager.requestWithBody(
            APIConfig.Endpoints.register,
            method: .post,
            body: request
        ) { (result: Result<AuthResponse, Error>) in
            switch result {
            case .success(let response):
                // Save tokens
                try? TokenManager.shared.saveAccessToken(response.accessToken)
                try? TokenManager.shared.saveRefreshToken(response.refreshToken)
                try? TokenManager.shared.saveUserId(response.userId)
                completion(.success(response))
            case .failure(let error):
                completion(.failure(error))
            }
        }
    }

    // MARK: - Login

    func login(
        email: String,
        password: String,
        completion: @escaping (Result<AuthResponse, Error>) -> Void
    ) {
        let request = LoginRequest(email: email, password: password)

        networkManager.requestWithBody(
            APIConfig.Endpoints.login,
            method: .post,
            body: request
        ) { (result: Result<AuthResponse, Error>) in
            switch result {
            case .success(let response):
                // Save tokens
                try? TokenManager.shared.saveAccessToken(response.accessToken)
                try? TokenManager.shared.saveRefreshToken(response.refreshToken)
                try? TokenManager.shared.saveUserId(response.userId)
                completion(.success(response))
            case .failure(let error):
                completion(.failure(error))
            }
        }
    }

    // MARK: - Refresh Token

    func refreshToken(completion: @escaping (Result<AuthResponse, Error>) -> Void) {
        guard let refreshToken = TokenManager.shared.getRefreshToken() else {
            completion(.failure(NSError(domain: "AuthService", code: -1, userInfo: [NSLocalizedDescriptionKey: "No refresh token available"])))
            return
        }

        let request = RefreshTokenRequest(refreshToken: refreshToken)

        networkManager.requestWithBody(
            APIConfig.Endpoints.refreshToken,
            method: .post,
            body: request
        ) { (result: Result<AuthResponse, Error>) in
            switch result {
            case .success(let response):
                // Save new tokens
                try? TokenManager.shared.saveAccessToken(response.accessToken)
                try? TokenManager.shared.saveRefreshToken(response.refreshToken)
                completion(.success(response))
            case .failure(let error):
                completion(.failure(error))
            }
        }
    }

    // MARK: - Logout

    func logout(completion: @escaping (Result<Void, Error>) -> Void) {
        networkManager.request(
            APIConfig.Endpoints.logout,
            method: .post
        ) { (result: Result<EmptyResponse, Error>) in
            // Clear tokens regardless of API result
            TokenManager.shared.clearAll()

            switch result {
            case .success:
                completion(.success(()))
            case .failure(let error):
                // Still succeed even if API call fails
                print("Logout API error (ignored): \(error)")
                completion(.success(()))
            }
        }
    }
}

// MARK: - Empty Response

struct EmptyResponse: Codable {}

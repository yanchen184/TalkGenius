//
//  NetworkManager.swift
//  TalkGenius
//
//  Network manager using Alamofire.
//

import Foundation
import Alamofire
import SwiftyJSON

class NetworkManager {

    static let shared = NetworkManager()

    private let session: Session

    private init() {
        // Configure session with interceptor
        let interceptor = AuthInterceptor()
        session = Session(interceptor: interceptor)
    }

    // MARK: - Generic Request

    func request<T: Decodable>(
        _ endpoint: String,
        method: HTTPMethod = .get,
        parameters: Parameters? = nil,
        encoding: ParameterEncoding = JSONEncoding.default,
        completion: @escaping (Result<T, Error>) -> Void
    ) {
        let url = APIConfig.baseURL + endpoint

        session.request(
            url,
            method: method,
            parameters: parameters,
            encoding: encoding
        )
        .validate()
        .responseDecodable(of: T.self) { response in
            switch response.result {
            case .success(let value):
                completion(.success(value))
            case .failure(let error):
                completion(.failure(error))
            }
        }
    }

    // MARK: - Request with Custom Body

    func requestWithBody<T: Decodable, U: Encodable>(
        _ endpoint: String,
        method: HTTPMethod = .post,
        body: U,
        completion: @escaping (Result<T, Error>) -> Void
    ) {
        let url = APIConfig.baseURL + endpoint

        session.request(
            url,
            method: method,
            parameters: body,
            encoder: JSONParameterEncoder.default
        )
        .validate()
        .responseDecodable(of: T.self) { response in
            switch response.result {
            case .success(let value):
                completion(.success(value))
            case .failure(let error):
                completion(.failure(error))
            }
        }
    }
}

// MARK: - Auth Interceptor

class AuthInterceptor: RequestInterceptor {

    func adapt(_ urlRequest: URLRequest, for session: Session, completion: @escaping (Result<URLRequest, Error>) -> Void) {
        var urlRequest = urlRequest

        // Skip token for auth endpoints
        if urlRequest.url?.path.contains("/auth/") == true {
            completion(.success(urlRequest))
            return
        }

        // Add access token to header
        if let accessToken = TokenManager.shared.getAccessToken() {
            urlRequest.setValue("Bearer \(accessToken)", forHTTPHeaderField: APIConfig.Headers.authorization)
        }

        completion(.success(urlRequest))
    }

    func retry(_ request: Request, for session: Session, dueTo error: Error, completion: @escaping (RetryResult) -> Void) {
        // Handle 401 Unauthorized - refresh token
        guard let response = request.task?.response as? HTTPURLResponse,
              response.statusCode == 401 else {
            completion(.doNotRetry)
            return
        }

        // TODO: Implement token refresh logic
        completion(.doNotRetry)
    }
}

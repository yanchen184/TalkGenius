//
//  AIService.swift
//  TalkGenius
//
//  AI reply generation API service.
//

import Foundation

class AIService {

    static let shared = AIService()
    private let networkManager = NetworkManager.shared

    private init() {}

    // MARK: - Generate Reply

    func generateReply(
        receivedMessage: String,
        toneStyle: ToneStyle,
        conversationContext: String? = nil,
        completion: @escaping (Result<GenerateReplyResponse, Error>) -> Void
    ) {
        let request = GenerateReplyRequest(
            receivedMessage: receivedMessage,
            toneStyle: toneStyle,
            conversationContext: conversationContext
        )

        networkManager.requestWithBody(
            APIConfig.Endpoints.generateReply,
            method: .post,
            body: request,
            completion: completion
        )
    }
}

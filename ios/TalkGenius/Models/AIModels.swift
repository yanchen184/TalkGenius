//
//  AIModels.swift
//  TalkGenius
//
//  AI reply generation models.
//

import Foundation

// MARK: - Request Models

struct GenerateReplyRequest: Codable {
    let receivedMessage: String
    let toneStyle: ToneStyle
    let conversationContext: String?
}

// MARK: - Response Models

struct GenerateReplyResponse: Codable {
    let conversationId: String
    let generatedReply: String
    let toneStyle: ToneStyle
    let timestamp: String
}

// MARK: - Conversation Model

struct Conversation: Codable {
    let id: String
    let userId: String
    let receivedMessage: String
    let generatedReply: String
    let toneStyle: ToneStyle
    let contextSnapshot: String?
    let createdAt: Date

    enum CodingKeys: String, CodingKey {
        case id
        case userId
        case receivedMessage
        case generatedReply
        case toneStyle
        case contextSnapshot
        case createdAt
    }
}

//
//  ToneStyle.swift
//  TalkGenius
//
//  AI conversation tone styles enum.
//

import Foundation

/// Enum representing different conversation tone styles.
/// Must match backend Conversation.ToneStyle enum.
enum ToneStyle: String, Codable, CaseIterable {
    case humorous = "Humorous"
    case highEQ = "High_EQ"
    case gentle = "Gentle"
    case cute = "Cute"
    case romantic = "Romantic"
    case professional = "Professional"
    case direct = "Direct"
    case flirty = "Flirty"

    var displayName: String {
        switch self {
        case .humorous:
            return "Humorous"
        case .highEQ:
            return "High EQ"
        case .gentle:
            return "Gentle"
        case .cute:
            return "Cute"
        case .romantic:
            return "Romantic"
        case .professional:
            return "Professional"
        case .direct:
            return "Direct"
        case .flirty:
            return "Flirty"
        }
    }

    var icon: String {
        switch self {
        case .humorous:
            return "😄"
        case .highEQ:
            return "🧠"
        case .gentle:
            return "😊"
        case .cute:
            return "🥰"
        case .romantic:
            return "💕"
        case .professional:
            return "💼"
        case .direct:
            return "🎯"
        case .flirty:
            return "😏"
        }
    }
}

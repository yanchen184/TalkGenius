//
//  KeyboardViewController.swift
//  TalkGeniusKeyboard
//
//  Custom keyboard view controller with AI reply suggestions.
//

import UIKit

class KeyboardViewController: UIInputViewController {

    // MARK: - Properties

    private var currentToneStyle: ToneStyle = .humorous
    private var isGeneratingReply = false

    // UI Elements
    private var aiButton: UIButton!
    private var toneStyleButton: UIButton!
    private var loadingIndicator: UIActivityIndicatorView!

    // MARK: - Lifecycle

    override func viewDidLoad() {
        super.viewDidLoad()
        setupKeyboard()
    }

    // MARK: - Setup

    private func setupKeyboard() {
        // Set keyboard height
        let keyboardHeight: CGFloat = 300

        // Create container view
        let containerView = UIView(frame: CGRect(x: 0, y: 0, width: view.frame.width, height: keyboardHeight))
        containerView.backgroundColor = .systemGray6

        // AI Suggestion Button
        aiButton = UIButton(type: .system)
        aiButton.frame = CGRect(x: 16, y: 16, width: 200, height: 44)
        aiButton.setTitle("✨ Generate AI Reply", for: .normal)
        aiButton.titleLabel?.font = UIFont.systemFont(ofSize: 16, weight: .semibold)
        aiButton.backgroundColor = .systemBlue
        aiButton.setTitleColor(.white, for: .normal)
        aiButton.layer.cornerRadius = 8
        aiButton.addTarget(self, action: #selector(aiButtonTapped), for: .touchUpInside)
        containerView.addSubview(aiButton)

        // Tone Style Button
        toneStyleButton = UIButton(type: .system)
        toneStyleButton.frame = CGRect(x: view.frame.width - 116, y: 16, width: 100, height: 44)
        toneStyleButton.setTitle(currentToneStyle.icon + " " + currentToneStyle.displayName, for: .normal)
        toneStyleButton.titleLabel?.font = UIFont.systemFont(ofSize: 14)
        toneStyleButton.backgroundColor = .systemGray5
        toneStyleButton.setTitleColor(.label, for: .normal)
        toneStyleButton.layer.cornerRadius = 8
        toneStyleButton.addTarget(self, action: #selector(toneStyleButtonTapped), for: .touchUpInside)
        containerView.addSubview(toneStyleButton)

        // Loading Indicator
        loadingIndicator = UIActivityIndicatorView(style: .medium)
        loadingIndicator.center = CGPoint(x: view.frame.width / 2, y: 80)
        loadingIndicator.hidesWhenStopped = true
        containerView.addSubview(loadingIndicator)

        // Info Label
        let infoLabel = UILabel(frame: CGRect(x: 16, y: 70, width: view.frame.width - 32, height: 60))
        infoLabel.text = "Select text in the messaging app, then tap 'Generate AI Reply' to get intelligent suggestions."
        infoLabel.font = UIFont.systemFont(ofSize: 14)
        infoLabel.textColor = .secondaryLabel
        infoLabel.numberOfLines = 0
        infoLabel.textAlignment = .center
        containerView.addSubview(infoLabel)

        // Add to input view
        view.addSubview(containerView)

        // Set keyboard constraints
        let heightConstraint = NSLayoutConstraint(
            item: view!,
            attribute: .height,
            relatedBy: .equal,
            toItem: nil,
            attribute: .notAnAttribute,
            multiplier: 0,
            constant: keyboardHeight
        )
        view.addConstraint(heightConstraint)
    }

    // MARK: - Actions

    @objc private func aiButtonTapped() {
        guard !isGeneratingReply else { return }

        // Get selected text or text before cursor
        guard let selectedText = getSelectedText(), !selectedText.isEmpty else {
            showAlert(message: "No text selected. Please select the message you received.")
            return
        }

        // Check authentication
        guard TokenManager.shared.isAuthenticated() else {
            showAlert(message: "Please login to use AI features.")
            return
        }

        // Start generating reply
        generateAIReply(for: selectedText)
    }

    @objc private func toneStyleButtonTapped() {
        showToneStylePicker()
    }

    // MARK: - AI Reply Generation

    private func generateAIReply(for receivedMessage: String) {
        isGeneratingReply = true
        aiButton.isEnabled = false
        loadingIndicator.startAnimating()

        // Build conversation context
        let context = buildConversationContext()

        // Call AI service
        AIService.shared.generateReply(
            receivedMessage: receivedMessage,
            toneStyle: currentToneStyle,
            conversationContext: context
        ) { [weak self] result in
            DispatchQueue.main.async {
                self?.isGeneratingReply = false
                self?.aiButton.isEnabled = true
                self?.loadingIndicator.stopAnimating()

                switch result {
                case .success(let response):
                    self?.insertGeneratedReply(response.generatedReply)
                    self?.saveConversation(response: response, receivedMessage: receivedMessage)
                case .failure(let error):
                    self?.showAlert(message: "Error generating reply: \(error.localizedDescription)")
                }
            }
        }
    }

    // MARK: - Helper Methods

    private func getSelectedText() -> String? {
        // Get text from document proxy
        if let selectedText = textDocumentProxy.selectedText, !selectedText.isEmpty {
            return selectedText
        }

        // Fallback: get text before cursor (last sentence)
        if let beforeText = textDocumentProxy.documentContextBeforeInput {
            // Extract last sentence
            let sentences = beforeText.components(separatedBy: CharacterSet(charactersIn: ".!?\n"))
            return sentences.last?.trimmingCharacters(in: .whitespacesAndNewlines)
        }

        return nil
    }

    private func insertGeneratedReply(_ reply: String) {
        textDocumentProxy.insertText(reply)
    }

    private func buildConversationContext() -> String? {
        guard let userId = TokenManager.shared.getUserId() else { return nil }

        let conversations = CoreDataManager.shared.getConversations(userId: userId, limit: 3)
        guard !conversations.isEmpty else { return nil }

        return conversations.reversed().map { conv in
            "Received: \(conv.receivedMessage)\nReplied: \(conv.generatedReply)"
        }.joined(separator: "\n")
    }

    private func saveConversation(response: GenerateReplyResponse, receivedMessage: String) {
        guard let userId = TokenManager.shared.getUserId() else { return }

        CoreDataManager.shared.saveConversation(
            conversationId: response.conversationId,
            userId: userId,
            receivedMessage: receivedMessage,
            generatedReply: response.generatedReply,
            toneStyle: response.toneStyle.rawValue,
            contextSnapshot: buildConversationContext()
        )
    }

    private func showToneStylePicker() {
        let alertController = UIAlertController(
            title: "Select Tone Style",
            message: "Choose how the AI should respond",
            preferredStyle: .actionSheet
        )

        for style in ToneStyle.allCases {
            let action = UIAlertAction(title: "\(style.icon) \(style.displayName)", style: .default) { [weak self] _ in
                self?.currentToneStyle = style
                self?.toneStyleButton.setTitle(style.icon + " " + style.displayName, for: .normal)
            }
            alertController.addAction(action)
        }

        alertController.addAction(UIAlertAction(title: "Cancel", style: .cancel))

        present(alertController, animated: true)
    }

    private func showAlert(message: String) {
        let alertController = UIAlertController(
            title: "TalkGenius",
            message: message,
            preferredStyle: .alert
        )
        alertController.addAction(UIAlertAction(title: "OK", style: .default))
        present(alertController, animated: true)
    }
}

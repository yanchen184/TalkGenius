//
//  DashboardViewController.swift
//  TalkGenius
//
//  Dashboard view controller.
//

import UIKit
import SnapKit

class DashboardViewController: UIViewController {

    // MARK: - UI Elements

    private let titleLabel = UILabel()
    private let welcomeLabel = UILabel()
    private let infoLabel = UILabel()
    private let enableKeyboardButton = UIButton(type: .system)
    private let logoutButton = UIButton(type: .system)

    // MARK: - Lifecycle

    override func viewDidLoad() {
        super.viewDidLoad()
        setupUI()
        setupConstraints()
        loadUserData()
    }

    // MARK: - Setup

    private func setupUI() {
        view.backgroundColor = .systemBackground
        title = "TalkGenius"

        // Title
        titleLabel.text = "TalkGenius AI Keyboard"
        titleLabel.font = UIFont.systemFont(ofSize: 28, weight: .bold)
        titleLabel.textAlignment = .center
        view.addSubview(titleLabel)

        // Welcome Label
        welcomeLabel.text = "Welcome to TalkGenius!"
        welcomeLabel.font = UIFont.systemFont(ofSize: 20, weight: .medium)
        welcomeLabel.textAlignment = .center
        view.addSubview(welcomeLabel)

        // Info Label
        infoLabel.text = "Enable the keyboard in system settings to start using AI-powered reply suggestions."
        infoLabel.font = UIFont.systemFont(ofSize: 16)
        infoLabel.textColor = .secondaryLabel
        infoLabel.textAlignment = .center
        infoLabel.numberOfLines = 0
        view.addSubview(infoLabel)

        // Enable Keyboard Button
        enableKeyboardButton.setTitle("Enable Keyboard", for: .normal)
        enableKeyboardButton.titleLabel?.font = UIFont.systemFont(ofSize: 18, weight: .semibold)
        enableKeyboardButton.backgroundColor = .systemBlue
        enableKeyboardButton.setTitleColor(.white, for: .normal)
        enableKeyboardButton.layer.cornerRadius = 8
        enableKeyboardButton.addTarget(self, action: #selector(enableKeyboardTapped), for: .touchUpInside)
        view.addSubview(enableKeyboardButton)

        // Logout Button
        logoutButton.setTitle("Logout", for: .normal)
        logoutButton.titleLabel?.font = UIFont.systemFont(ofSize: 16)
        logoutButton.setTitleColor(.systemRed, for: .normal)
        logoutButton.layer.borderWidth = 1
        logoutButton.layer.borderColor = UIColor.systemRed.cgColor
        logoutButton.layer.cornerRadius = 8
        logoutButton.addTarget(self, action: #selector(logoutTapped), for: .touchUpInside)
        view.addSubview(logoutButton)
    }

    private func setupConstraints() {
        titleLabel.snp.makeConstraints { make in
            make.top.equalTo(view.safeAreaLayoutGuide).offset(60)
            make.centerX.equalToSuperview()
        }

        welcomeLabel.snp.makeConstraints { make in
            make.top.equalTo(titleLabel.snp.bottom).offset(40)
            make.centerX.equalToSuperview()
        }

        infoLabel.snp.makeConstraints { make in
            make.top.equalTo(welcomeLabel.snp.bottom).offset(20)
            make.leading.equalToSuperview().offset(24)
            make.trailing.equalToSuperview().offset(-24)
        }

        enableKeyboardButton.snp.makeConstraints { make in
            make.top.equalTo(infoLabel.snp.bottom).offset(40)
            make.leading.equalToSuperview().offset(24)
            make.trailing.equalToSuperview().offset(-24)
            make.height.equalTo(50)
        }

        logoutButton.snp.makeConstraints { make in
            make.top.equalTo(enableKeyboardButton.snp.bottom).offset(16)
            make.leading.trailing.height.equalTo(enableKeyboardButton)
        }
    }

    // MARK: - Data Loading

    private func loadUserData() {
        guard let userId = TokenManager.shared.getUserId(),
              let user = CoreDataManager.shared.getUser(userId: userId) else {
            return
        }

        if let displayName = user.displayName {
            welcomeLabel.text = "Welcome, \(displayName)!"
        }
    }

    // MARK: - Actions

    @objc private func enableKeyboardTapped() {
        // Open keyboard settings
        if let url = URL(string: UIApplication.openSettingsURLString) {
            UIApplication.shared.open(url)
        }
    }

    @objc private func logoutTapped() {
        let alert = UIAlertController(
            title: "Logout",
            message: "Are you sure you want to logout?",
            preferredStyle: .alert
        )

        alert.addAction(UIAlertAction(title: "Cancel", style: .cancel))
        alert.addAction(UIAlertAction(title: "Logout", style: .destructive) { [weak self] _ in
            self?.performLogout()
        })

        present(alert, animated: true)
    }

    private func performLogout() {
        AuthService.shared.logout { [weak self] result in
            DispatchQueue.main.async {
                // Navigate back to auth screen
                let authVC = AuthViewController()
                let navController = UINavigationController(rootViewController: authVC)
                navController.modalPresentationStyle = .fullScreen
                self?.present(navController, animated: true)
            }
        }
    }
}

//
//  AuthViewController.swift
//  TalkGenius
//
//  Authentication view controller (Login/Register).
//

import UIKit
import SnapKit

class AuthViewController: UIViewController {

    // MARK: - UI Elements

    private let titleLabel = UILabel()
    private let segmentedControl = UISegmentedControl(items: ["Login", "Register"])
    private let emailTextField = UITextField()
    private let passwordTextField = UITextField()
    private let displayNameTextField = UITextField()
    private let submitButton = UIButton(type: .system)
    private let loadingIndicator = UIActivityIndicatorView(style: .medium)
    private let errorLabel = UILabel()

    // MARK: - Properties

    private var isLoginMode = true

    // MARK: - Lifecycle

    override func viewDidLoad() {
        super.viewDidLoad()
        setupUI()
        setupConstraints()
    }

    // MARK: - Setup

    private func setupUI() {
        view.backgroundColor = .systemBackground

        // Title
        titleLabel.text = "TalkGenius"
        titleLabel.font = UIFont.systemFont(ofSize: 32, weight: .bold)
        titleLabel.textAlignment = .center
        view.addSubview(titleLabel)

        // Segmented Control
        segmentedControl.selectedSegmentIndex = 0
        segmentedControl.addTarget(self, action: #selector(segmentChanged), for: .valueChanged)
        view.addSubview(segmentedControl)

        // Email TextField
        emailTextField.placeholder = "Email"
        emailTextField.borderStyle = .roundedRect
        emailTextField.keyboardType = .emailAddress
        emailTextField.autocapitalizationType = .none
        view.addSubview(emailTextField)

        // Password TextField
        passwordTextField.placeholder = "Password"
        passwordTextField.borderStyle = .roundedRect
        passwordTextField.isSecureTextEntry = true
        view.addSubview(passwordTextField)

        // Display Name TextField (hidden initially)
        displayNameTextField.placeholder = "Display Name (Optional)"
        displayNameTextField.borderStyle = .roundedRect
        displayNameTextField.isHidden = true
        view.addSubview(displayNameTextField)

        // Submit Button
        submitButton.setTitle("Login", for: .normal)
        submitButton.titleLabel?.font = UIFont.systemFont(ofSize: 18, weight: .semibold)
        submitButton.backgroundColor = .systemBlue
        submitButton.setTitleColor(.white, for: .normal)
        submitButton.layer.cornerRadius = 8
        submitButton.addTarget(self, action: #selector(submitButtonTapped), for: .touchUpInside)
        view.addSubview(submitButton)

        // Loading Indicator
        loadingIndicator.hidesWhenStopped = true
        view.addSubview(loadingIndicator)

        // Error Label
        errorLabel.textColor = .systemRed
        errorLabel.font = UIFont.systemFont(ofSize: 14)
        errorLabel.textAlignment = .center
        errorLabel.numberOfLines = 0
        errorLabel.isHidden = true
        view.addSubview(errorLabel)
    }

    private func setupConstraints() {
        titleLabel.snp.makeConstraints { make in
            make.top.equalTo(view.safeAreaLayoutGuide).offset(60)
            make.centerX.equalToSuperview()
        }

        segmentedControl.snp.makeConstraints { make in
            make.top.equalTo(titleLabel.snp.bottom).offset(40)
            make.leading.equalToSuperview().offset(24)
            make.trailing.equalToSuperview().offset(-24)
        }

        emailTextField.snp.makeConstraints { make in
            make.top.equalTo(segmentedControl.snp.bottom).offset(32)
            make.leading.equalToSuperview().offset(24)
            make.trailing.equalToSuperview().offset(-24)
            make.height.equalTo(44)
        }

        passwordTextField.snp.makeConstraints { make in
            make.top.equalTo(emailTextField.snp.bottom).offset(16)
            make.leading.trailing.height.equalTo(emailTextField)
        }

        displayNameTextField.snp.makeConstraints { make in
            make.top.equalTo(passwordTextField.snp.bottom).offset(16)
            make.leading.trailing.height.equalTo(emailTextField)
        }

        submitButton.snp.makeConstraints { make in
            make.top.equalTo(displayNameTextField.snp.bottom).offset(32)
            make.leading.trailing.equalTo(emailTextField)
            make.height.equalTo(50)
        }

        loadingIndicator.snp.makeConstraints { make in
            make.centerX.equalToSuperview()
            make.top.equalTo(submitButton.snp.bottom).offset(16)
        }

        errorLabel.snp.makeConstraints { make in
            make.top.equalTo(loadingIndicator.snp.bottom).offset(16)
            make.leading.equalToSuperview().offset(24)
            make.trailing.equalToSuperview().offset(-24)
        }
    }

    // MARK: - Actions

    @objc private func segmentChanged() {
        isLoginMode = segmentedControl.selectedSegmentIndex == 0
        displayNameTextField.isHidden = isLoginMode
        submitButton.setTitle(isLoginMode ? "Login" : "Register", for: .normal)
        errorLabel.isHidden = true

        // Update submit button constraint
        submitButton.snp.remakeConstraints { make in
            if isLoginMode {
                make.top.equalTo(passwordTextField.snp.bottom).offset(32)
            } else {
                make.top.equalTo(displayNameTextField.snp.bottom).offset(32)
            }
            make.leading.trailing.equalTo(emailTextField)
            make.height.equalTo(50)
        }
    }

    @objc private func submitButtonTapped() {
        guard let email = emailTextField.text, !email.isEmpty,
              let password = passwordTextField.text, !password.isEmpty else {
            showError("Please enter email and password")
            return
        }

        errorLabel.isHidden = true
        submitButton.isEnabled = false
        loadingIndicator.startAnimating()

        if isLoginMode {
            login(email: email, password: password)
        } else {
            register(email: email, password: password, displayName: displayNameTextField.text)
        }
    }

    // MARK: - API Calls

    private func login(email: String, password: String) {
        AuthService.shared.login(email: email, password: password) { [weak self] result in
            DispatchQueue.main.async {
                self?.submitButton.isEnabled = true
                self?.loadingIndicator.stopAnimating()

                switch result {
                case .success(let response):
                    self?.saveUserData(response: response)
                    self?.navigateToDashboard()
                case .failure(let error):
                    self?.showError("Login failed: \(error.localizedDescription)")
                }
            }
        }
    }

    private func register(email: String, password: String, displayName: String?) {
        AuthService.shared.register(
            email: email,
            password: password,
            displayName: displayName
        ) { [weak self] result in
            DispatchQueue.main.async {
                self?.submitButton.isEnabled = true
                self?.loadingIndicator.stopAnimating()

                switch result {
                case .success(let response):
                    self?.saveUserData(response: response)
                    self?.navigateToDashboard()
                case .failure(let error):
                    self?.showError("Registration failed: \(error.localizedDescription)")
                }
            }
        }
    }

    // MARK: - Helper Methods

    private func saveUserData(response: AuthResponse) {
        CoreDataManager.shared.saveUser(
            userId: response.userId,
            email: response.email,
            displayName: response.displayName,
            isPremium: response.isPremium
        )
    }

    private func navigateToDashboard() {
        let dashboardVC = DashboardViewController()
        let navController = UINavigationController(rootViewController: dashboardVC)
        navController.modalPresentationStyle = .fullScreen
        present(navController, animated: true)
    }

    private func showError(_ message: String) {
        errorLabel.text = message
        errorLabel.isHidden = false
    }
}

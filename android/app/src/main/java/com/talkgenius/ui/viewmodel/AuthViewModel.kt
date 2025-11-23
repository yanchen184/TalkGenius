package com.talkgenius.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.talkgenius.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for authentication operations.
 */
@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _authState = MutableStateFlow(AuthState())
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    init {
        checkAuthStatus()
    }

    /**
     * Check if user is authenticated.
     */
    private fun checkAuthStatus() {
        viewModelScope.launch {
            val isAuthenticated = authRepository.isAuthenticated()
            _authState.value = _authState.value.copy(isAuthenticated = isAuthenticated)
        }
    }

    /**
     * Register a new user.
     */
    fun register(
        email: String,
        password: String,
        displayName: String? = null,
        gender: String? = null,
        age: Int? = null
    ) {
        viewModelScope.launch {
            _authState.value = _authState.value.copy(isLoading = true, error = null)

            val result = authRepository.register(email, password, displayName, gender, age)

            result.fold(
                onSuccess = { response ->
                    _authState.value = AuthState(
                        isAuthenticated = true,
                        userId = response.userId,
                        email = response.email,
                        displayName = response.displayName,
                        isPremium = response.isPremium,
                        isLoading = false
                    )
                },
                onFailure = { exception ->
                    _authState.value = _authState.value.copy(
                        error = exception.message ?: "Registration failed",
                        isLoading = false
                    )
                }
            )
        }
    }

    /**
     * Login existing user.
     */
    fun login(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = _authState.value.copy(isLoading = true, error = null)

            val result = authRepository.login(email, password)

            result.fold(
                onSuccess = { response ->
                    _authState.value = AuthState(
                        isAuthenticated = true,
                        userId = response.userId,
                        email = response.email,
                        displayName = response.displayName,
                        isPremium = response.isPremium,
                        isLoading = false
                    )
                },
                onFailure = { exception ->
                    _authState.value = _authState.value.copy(
                        error = exception.message ?: "Login failed",
                        isLoading = false
                    )
                }
            )
        }
    }

    /**
     * Logout user.
     */
    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
            _authState.value = AuthState()
        }
    }

    /**
     * Clear error message.
     */
    fun clearError() {
        _authState.value = _authState.value.copy(error = null)
    }
}

/**
 * State for authentication.
 */
data class AuthState(
    val isAuthenticated: Boolean = false,
    val userId: String? = null,
    val email: String? = null,
    val displayName: String? = null,
    val isPremium: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null
)

package com.talkgenius.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.talkgenius.ui.viewmodel.AuthViewModel

/**
 * Authentication screen with login and register tabs.
 */
@Composable
fun AuthScreen(viewModel: AuthViewModel) {
    var selectedTab by remember { mutableStateOf(0) }
    val authState by viewModel.authState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "TalkGenius",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(top = 48.dp, bottom = 32.dp)
        )

        TabRow(selectedTabIndex = selectedTab) {
            Tab(
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                text = { Text("Login") }
            )
            Tab(
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                text = { Text("Register") }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        when (selectedTab) {
            0 -> LoginForm(viewModel = viewModel, authState = authState)
            1 -> RegisterForm(viewModel = viewModel, authState = authState)
        }

        // Show error if exists
        authState.error?.let { error ->
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        // Show loading indicator
        if (authState.isLoading) {
            Spacer(modifier = Modifier.height(16.dp))
            CircularProgressIndicator()
        }
    }
}

@Composable
fun LoginForm(viewModel: AuthViewModel, authState: com.talkgenius.ui.viewmodel.AuthState) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            enabled = !authState.isLoading
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            enabled = !authState.isLoading
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                viewModel.login(email, password)
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !authState.isLoading && email.isNotBlank() && password.isNotBlank()
        ) {
            Text("Login")
        }
    }
}

@Composable
fun RegisterForm(viewModel: AuthViewModel, authState: com.talkgenius.ui.viewmodel.AuthState) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var displayName by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            enabled = !authState.isLoading
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            enabled = !authState.isLoading
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = displayName,
            onValueChange = { displayName = it },
            label = { Text("Display Name (Optional)") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            enabled = !authState.isLoading
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                viewModel.register(
                    email = email,
                    password = password,
                    displayName = displayName.ifBlank { null }
                )
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !authState.isLoading && email.isNotBlank() && password.isNotBlank()
        ) {
            Text("Register")
        }
    }
}

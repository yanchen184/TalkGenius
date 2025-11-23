package com.talkgenius.ui

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.talkgenius.ui.theme.TalkGeniusTheme
import com.talkgenius.ui.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * Main activity for TalkGenius application.
 * Shows authentication screens or dashboard based on auth state.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TalkGeniusTheme {
                MainScreen()
            }
        }
    }

    @Composable
    fun MainScreen() {
        val authViewModel: AuthViewModel = hiltViewModel()
        val authState by authViewModel.authState.collectAsState()

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            if (authState.isAuthenticated) {
                DashboardScreen(
                    onLogout = { authViewModel.logout() },
                    onOpenKeyboardSettings = { openKeyboardSettings() }
                )
            } else {
                AuthScreen(viewModel = authViewModel)
            }
        }
    }

    @Composable
    fun DashboardScreen(
        onLogout: () -> Unit,
        onOpenKeyboardSettings: () -> Unit
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "TalkGenius AI Keyboard",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Welcome to TalkGenius!",
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Enable the keyboard in system settings to start using AI-powered reply suggestions.",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = onOpenKeyboardSettings,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Enable Keyboard")
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedButton(
                onClick = onLogout,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Logout")
            }
        }
    }

    private fun openKeyboardSettings() {
        startActivity(Intent(Settings.ACTION_INPUT_METHOD_SETTINGS))
    }
}

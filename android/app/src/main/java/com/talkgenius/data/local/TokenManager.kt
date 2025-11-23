package com.talkgenius.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Manages JWT tokens using DataStore (encrypted preferences).
 */
@Singleton
class TokenManager @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "auth_prefs")

    companion object {
        private val ACCESS_TOKEN_KEY = stringPreferencesKey("access_token")
        private val REFRESH_TOKEN_KEY = stringPreferencesKey("refresh_token")
        private val USER_ID_KEY = stringPreferencesKey("user_id")
    }

    /**
     * Save access token.
     */
    suspend fun saveAccessToken(token: String) {
        context.dataStore.edit { prefs ->
            prefs[ACCESS_TOKEN_KEY] = token
        }
    }

    /**
     * Save refresh token.
     */
    suspend fun saveRefreshToken(token: String) {
        context.dataStore.edit { prefs ->
            prefs[REFRESH_TOKEN_KEY] = token
        }
    }

    /**
     * Save user ID.
     */
    suspend fun saveUserId(userId: String) {
        context.dataStore.edit { prefs ->
            prefs[USER_ID_KEY] = userId
        }
    }

    /**
     * Get access token as Flow.
     */
    fun getAccessToken(): Flow<String?> {
        return context.dataStore.data.map { prefs ->
            prefs[ACCESS_TOKEN_KEY]
        }
    }

    /**
     * Get refresh token as Flow.
     */
    fun getRefreshToken(): Flow<String?> {
        return context.dataStore.data.map { prefs ->
            prefs[REFRESH_TOKEN_KEY]
        }
    }

    /**
     * Get user ID as Flow.
     */
    fun getUserId(): Flow<String?> {
        return context.dataStore.data.map { prefs ->
            prefs[USER_ID_KEY]
        }
    }

    /**
     * Clear all tokens (logout).
     */
    suspend fun clearTokens() {
        context.dataStore.edit { prefs ->
            prefs.clear()
        }
    }
}

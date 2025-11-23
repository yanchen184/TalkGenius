package com.talkgenius

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Application class for TalkGenius.
 * Annotated with @HiltAndroidApp to enable Hilt dependency injection.
 */
@HiltAndroidApp
class TalkGeniusApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        // Application-level initialization here
    }
}

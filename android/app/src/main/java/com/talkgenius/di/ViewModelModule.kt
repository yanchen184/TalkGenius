package com.talkgenius.di

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ServiceComponent

/**
 * Hilt module for ViewModel dependencies.
 */
@Module
@InstallIn(ActivityComponent::class, ServiceComponent::class)
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(
        factory: ViewModelProvider.Factory
    ): ViewModelProvider.Factory
}

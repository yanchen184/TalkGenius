package com.talkgenius.di

import android.content.Context
import androidx.room.Room
import com.talkgenius.data.local.TalkGeniusDatabase
import com.talkgenius.data.local.dao.ConversationDao
import com.talkgenius.data.local.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt module providing database-related dependencies.
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): TalkGeniusDatabase {
        return Room.databaseBuilder(
            context,
            TalkGeniusDatabase::class.java,
            TalkGeniusDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideUserDao(database: TalkGeniusDatabase): UserDao {
        return database.userDao()
    }

    @Provides
    @Singleton
    fun provideConversationDao(database: TalkGeniusDatabase): ConversationDao {
        return database.conversationDao()
    }
}

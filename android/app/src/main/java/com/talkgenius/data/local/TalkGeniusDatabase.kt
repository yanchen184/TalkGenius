package com.talkgenius.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.talkgenius.data.local.dao.ConversationDao
import com.talkgenius.data.local.dao.UserDao
import com.talkgenius.data.local.entity.ConversationEntity
import com.talkgenius.data.local.entity.UserEntity
import com.talkgenius.data.model.ToneStyle

/**
 * Room database for TalkGenius application.
 */
@Database(
    entities = [UserEntity::class, ConversationEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(TalkGeniusDatabase.Converters::class)
abstract class TalkGeniusDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun conversationDao(): ConversationDao

    /**
     * Type converters for Room database.
     */
    class Converters {
        @TypeConverter
        fun fromToneStyle(value: ToneStyle): String {
            return value.name
        }

        @TypeConverter
        fun toToneStyle(value: String): ToneStyle {
            return ToneStyle.valueOf(value)
        }
    }

    companion object {
        const val DATABASE_NAME = "talkgenius_db"
    }
}

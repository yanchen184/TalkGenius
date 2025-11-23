package com.talkgenius.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.talkgenius.data.model.ToneStyle

/**
 * Room entity for storing conversation history locally.
 */
@Entity(
    tableName = "conversations",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["user_id"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("user_id"), Index("created_at")]
)
data class ConversationEntity(
    @PrimaryKey
    @ColumnInfo(name = "conversation_id")
    val conversationId: String,

    @ColumnInfo(name = "user_id")
    val userId: String,

    @ColumnInfo(name = "received_message")
    val receivedMessage: String,

    @ColumnInfo(name = "generated_reply")
    val generatedReply: String,

    @ColumnInfo(name = "tone_style")
    val toneStyle: ToneStyle,

    @ColumnInfo(name = "context_snapshot")
    val contextSnapshot: String? = null,

    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis()
)

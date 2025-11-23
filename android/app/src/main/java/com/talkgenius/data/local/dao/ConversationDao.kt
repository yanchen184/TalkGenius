package com.talkgenius.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.talkgenius.data.local.entity.ConversationEntity
import kotlinx.coroutines.flow.Flow

/**
 * DAO for Conversation entity operations.
 */
@Dao
interface ConversationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertConversation(conversation: ConversationEntity)

    @Query("SELECT * FROM conversations WHERE user_id = :userId ORDER BY created_at DESC")
    fun getConversationsByUser(userId: String): Flow<List<ConversationEntity>>

    @Query("SELECT * FROM conversations WHERE conversation_id = :conversationId")
    suspend fun getConversationById(conversationId: String): ConversationEntity?

    @Query("SELECT * FROM conversations WHERE user_id = :userId ORDER BY created_at DESC LIMIT :limit")
    suspend fun getRecentConversations(userId: String, limit: Int = 10): List<ConversationEntity>

    @Query("DELETE FROM conversations WHERE conversation_id = :conversationId")
    suspend fun deleteConversation(conversationId: String)

    @Query("DELETE FROM conversations WHERE user_id = :userId")
    suspend fun deleteUserConversations(userId: String)

    @Query("DELETE FROM conversations")
    suspend fun deleteAllConversations()
}

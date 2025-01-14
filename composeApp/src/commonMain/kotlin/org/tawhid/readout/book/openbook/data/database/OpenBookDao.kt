package org.tawhid.readout.book.openbook.data.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import org.tawhid.readout.book.audiobook.data.database.AudioBookEntity

@Dao
interface OpenBookDao {
    @Upsert
    suspend fun upsert(openBook: OpenBookEntity)

    @Query("SELECT * FROM OpenBookEntity")
    fun getAllBooks(): Flow<List<OpenBookEntity>>

    @Query("SELECT * FROM OpenBookEntity WHERE isSaved = 1")
    fun getSavedBooks(): Flow<List<OpenBookEntity>>

    @Query("SELECT * FROM OpenBookEntity WHERE id = :id")
    suspend fun getSavedBook(id: String): OpenBookEntity?

    @Query("SELECT * FROM OpenBookEntity WHERE id = :id LIMIT 1")
    fun getSavedBookById(id: String): Flow<OpenBookEntity?>

    @Query("SELECT * FROM OpenBookEntity WHERE bookType = :bookType")
    suspend fun getSavedBooksByType(bookType: String): List<OpenBookEntity>

    @Query("DELETE FROM OpenBookEntity WHERE id = :id AND isSaved = 1")
    suspend fun deleteSavedBook(id: String)

    @Query("DELETE FROM OpenBookEntity WHERE bookType = :bookType AND (isSaved IS NULL OR isSaved = 0)")
    suspend fun deleteBooksByType(bookType: String)

    @Query("UPDATE OpenBookEntity SET isSaved = :isSaved, timeStamp = :timeStamp WHERE id = :id")
    suspend fun updateIsSaved(id: String, isSaved: Boolean, timeStamp: Long)

    @Query("DELETE FROM OpenBookEntity")
    suspend fun clearAll()
}
package org.tawhid.readout.book.audiobook.data.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface AudioBookDao {
    @Upsert
    suspend fun upsert(audioBook: AudioBookEntity)

    @Upsert
    suspend fun upsertRecentlyReleasedBooks(audioBooks: List<AudioBookEntity>)

    @Query("SELECT * FROM AudioBookEntity WHERE isSaved = 1")
    fun getSavedBooks(): Flow<List<AudioBookEntity>>

    @Query("SELECT * FROM AudioBookEntity")
    fun getAllBooks(): Flow<List<AudioBookEntity>>

    @Query("SELECT * FROM AudioBookEntity WHERE bookType = :bookType")
    suspend fun getSavedBooksByType(bookType: String): List<AudioBookEntity>

    @Query("SELECT * FROM AudioBookEntity WHERE isViewed = 1")
    fun getViewedBooks(): Flow<List<AudioBookEntity>>

    @Query("DELETE FROM AudioBookEntity WHERE bookType = :bookType AND (isSaved IS NULL OR isSaved = 0) AND (isViewed IS NULL OR isViewed = 0)")
    suspend fun deleteBooksByType(bookType: String)

    @Query("SELECT * FROM AudioBookEntity WHERE id = :id LIMIT 1")
    fun getSavedBookById(id: String): Flow<AudioBookEntity?>

    @Query("DELETE FROM AudioBookEntity WHERE id = :id AND isSaved = 1")
    suspend fun deleteSavedBook(id: String)

    @Query("DELETE FROM AudioBookEntity")
    suspend fun clearAll()
}
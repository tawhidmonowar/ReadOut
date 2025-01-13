package org.tawhid.readout.book.audiobook.data.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface AudioBookDao {
    @Upsert
    suspend fun upsert(audioBook: AudioBookEntity)

    @Query("SELECT * FROM AudioBookEntity")
    fun getSavedBooks(): Flow<List<AudioBookEntity>>

    @Query("SELECT * FROM AudioBookEntity")
    fun getAllBooks(): Flow<List<AudioBookEntity>>

    @Query("SELECT * FROM AudioBookEntity WHERE id = :id LIMIT 1")
    fun getSavedBookById(id: String): Flow<AudioBookEntity?>

    @Query("DELETE FROM AudioBookEntity WHERE id = :id")
    suspend fun deleteSavedBook(id: String)

    @Query("DELETE FROM AudioBookEntity")
    suspend fun clearAll()
}
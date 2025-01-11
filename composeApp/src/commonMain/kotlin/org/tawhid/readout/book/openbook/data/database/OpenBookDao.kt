package org.tawhid.readout.book.openbook.data.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface OpenBookDao {
    @Upsert
    suspend fun upsert(openBook: OpenBookEntity)

    @Query("SELECT * FROM OpenBookEntity WHERE isSaved = 1")
    fun getSavedBooks(): Flow<List<OpenBookEntity>>

    @Query("SELECT * FROM OpenBookEntity WHERE id = :id")
    suspend fun getSavedBook(id: String): OpenBookEntity?

    @Query("DELETE FROM OpenBookEntity WHERE id = :id")
    suspend fun deleteSavedBook(id: String)

    @Query("UPDATE OpenBookEntity SET isSaved = :isSaved, timeStamp = :timeStamp WHERE id = :id")
    suspend fun updateIsSaved(id: String, isSaved: Boolean, timeStamp: Long)
}
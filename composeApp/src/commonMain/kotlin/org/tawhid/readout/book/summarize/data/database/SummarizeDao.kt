package org.tawhid.readout.book.summarize.data.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface SummarizeDao {
    @Upsert
    suspend fun upsert(summarize: SummarizeEntity)

    @Query("SELECT * FROM SummarizeEntity")
    fun getSummary(): Flow<List<SummarizeEntity>>

    @Query("DELETE FROM SummarizeEntity")
    suspend fun deleteAllSummary()

}
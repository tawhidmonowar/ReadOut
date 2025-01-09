package org.tawhid.readout.book.summarize.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SummarizeEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val title: String?,
    val authors: String?,
    val image: String?,
    val summary: String?,
    val description: String?,
    val timeStamp: Long?
)
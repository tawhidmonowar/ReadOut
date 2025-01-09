package org.tawhid.readout.book.openbook.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class OpenBookEntity(
    @PrimaryKey(autoGenerate = false) val id: String,
    val title: String,
    val imgUrl: String,
    val authors: List<String>,
    val description: String?,
    val languages: List<String>,
    val firstPublishYear: String?,
    val avgRating: Double?,
    val ratingCount: Int?,
    val numPages: Int?,
    val numEditions: Int,

    val bookType: String?,
    val isSaved: Boolean?,
    val isViewed: Boolean?,
    val summaryText: String?,
    val summaryBase64: String?,
    val timeStamp: Long?
)
package org.tawhid.readout.book.openbook.domain.entity

data class Book(
    val id: String,
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
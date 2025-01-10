package org.tawhid.readout.app.home.domain

data class RecentlyViewedBooks(
    val id: String,
    val title: String?,
    val imgUrl: String?,
    val authors: List<String>,
    val description: String?,
    val languages: String?,
    val timeStamp: Long?,
    val type: String?
)
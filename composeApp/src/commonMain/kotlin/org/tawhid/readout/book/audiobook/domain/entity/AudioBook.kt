package org.tawhid.readout.book.audiobook.domain.entity

data class AudioBook(
    val id: String,
    val title: String,
    val description: String?,
    val language: String?,
    val copyrightYear: String?,
    val numSections: String?,
    val textSourceUrl: String?,
    val zipFileUrl: String?,
    val libriVoxUrl: String?,
    val totalTime: String?,
    val imgUrl: String?,
    val authors: List<String>,

    val bookType: String?,
    val isSaved: Boolean?,
    val isViewed: Boolean?,
    val audioBookTracks: List<AudioBookTrack>?,
    val summaryText: String?,
    val summaryBase64: String?,
    val timeStamp: Long?
)
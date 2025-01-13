package org.tawhid.readout.book.audiobook.domain.entity

data class AudioBookTrack(
    val id: String,
    val sectionNumber: String?,
    val title: String?,
    val listenUrl: String?,
    val language: String?,
    val playtime: String?
)
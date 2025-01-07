package org.tawhid.readout.book.audiobook.domain

data class AudioBookTracks(
    val id: String,
    val sectionNumber: String?,
    val title: String?,
    val listenUrl: String?,
    val language: String?,
    val playtime: String?
)
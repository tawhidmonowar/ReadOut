package org.tawhid.readout.book.audiobook.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AudioBookTrackDto(
    @SerialName("id") val id: String,
    @SerialName("section_number") val sectionNumber: String?,
    @SerialName("title") val title: String?,
    @SerialName("listen_url") val listenUrl: String?,
    @SerialName("language") val language: String?,
    @SerialName("playtime") val playtime: String?
)
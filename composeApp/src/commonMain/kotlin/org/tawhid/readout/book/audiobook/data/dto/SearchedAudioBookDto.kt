package org.tawhid.readout.book.audiobook.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchedAudioBookDto(
    @SerialName("id") val id: String,
    @SerialName("title") val title: String,
    @SerialName("authors") val authors: List<AuthorDto>? = null,
    @SerialName("description") val description: String?,
    @SerialName("language") val language: String?,
    @SerialName("copyright_year") val copyrightYear: String?,
    @SerialName("num_sections") val numSections: String?,
    @SerialName("url_text_source") val textSourceUrl: String?,
    @SerialName("url_zip_file") val zipFileUrl: String?,
    @SerialName("url_librivox") val libriVoxUrl: String?,
    @SerialName("totaltime") val totalTime: String?,
    @SerialName("coverart_jpg") val coverImg: String?,
)
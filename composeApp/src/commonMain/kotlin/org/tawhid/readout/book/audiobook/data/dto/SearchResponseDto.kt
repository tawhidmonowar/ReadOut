package org.tawhid.readout.book.audiobook.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchResponseDto(
    @SerialName("books") val results: List<SearchedAudioBookDto>
)
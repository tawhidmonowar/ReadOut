package org.tawhid.readout.book.openbook.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BrowseResponseDto(
    @SerialName("works") val results: List<SearchedBookDto>
)
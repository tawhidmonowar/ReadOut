package org.tawhid.readout.book.openbook.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthorDto(
    @SerialName("key") val key: String,
    @SerialName("name") val name: String
)
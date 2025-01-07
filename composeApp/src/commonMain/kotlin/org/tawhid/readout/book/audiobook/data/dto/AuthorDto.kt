package org.tawhid.readout.book.audiobook.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthorDto(
    @SerialName("first_name") val firstName: String,
    @SerialName("last_name") val lastName: String,
)
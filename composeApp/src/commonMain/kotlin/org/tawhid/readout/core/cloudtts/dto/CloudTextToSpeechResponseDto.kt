package org.tawhid.readout.core.cloudtts.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CloudTextToSpeechResponseDto(
    @SerialName("audioContent") val audioContent: String
)
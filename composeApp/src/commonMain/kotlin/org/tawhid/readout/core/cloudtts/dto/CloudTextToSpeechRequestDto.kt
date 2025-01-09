package org.tawhid.readout.core.cloudtts.dto

import kotlinx.serialization.Serializable

@Serializable
data class CloudTextToSpeechRequestDto(
    val input: Input,
    val audioConfig: AudioConfig,
    val voice: Voice
)

@Serializable
data class Input(
    val text: String
)

@Serializable
data class AudioConfig(
    val audioEncoding: String,
    val pitch: Int,
    val speakingRate: Int
)

@Serializable
data class Voice(
    val languageCode: String,
    val name: String
)
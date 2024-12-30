package org.tawhid.readout.core.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val shimmer = Color(0xFFC3C3C3)
val shimmerColors = listOf(
    shimmer.copy(alpha = 0.3f),
    shimmer.copy(alpha = 0.5f),
    shimmer.copy(alpha = 1.0f),
    shimmer.copy(alpha = 0.5f),
    shimmer.copy(alpha = 0.3f),
)

val DarkColorScheme = darkColorScheme()
val LightColorScheme = lightColorScheme()
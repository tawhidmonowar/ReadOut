package org.tawhid.readout.core.player.presentation

data class PlayerState(
    val isLoading: Boolean = false,
    val isPlaying: Boolean = false,
    val isPaused: Boolean = true,
    val isCollapsed: Boolean = false,
    val nowPlaying: String? = "Unknown",
    val imageUrl: String? = null,
)
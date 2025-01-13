package org.tawhid.readout.core.player.presentation

data class PlayerState(
    val isLoading: Boolean = false,
    val isPlaying: Boolean = true,
    val isPaused: Boolean = false,
    val isCollapsed: Boolean = false,
    val nowPlaying: String = "Unknown",
)
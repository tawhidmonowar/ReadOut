package org.tawhid.readout.core.player.presentation

import org.tawhid.readout.core.player.domain.Player

data class PlayerState(
    val isLoading: Boolean = false,
    val isPlaying: Boolean = false,
    val isCollapsed: Boolean = false,
    val selectedPlayer: Player? = null,
    val nowPlaying: String? = null,
)
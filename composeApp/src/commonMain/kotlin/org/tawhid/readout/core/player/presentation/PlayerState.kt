package org.tawhid.readout.core.player.presentation

import org.tawhid.readout.core.player.domain.PlayerComponent

data class PlayerState(
    val isLoading: Boolean = false,
    val isPlaying: Boolean = false,
    val isCollapsed: Boolean = false,
    val selectedPlayerComponent: PlayerComponent? = null,
)
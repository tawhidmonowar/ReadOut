package org.tawhid.readout.core.player.presentation

import org.tawhid.readout.core.player.domain.PlayerComponent

sealed interface PlayerAction {
    data class OnPlayClick(val audioUrl: String) : PlayerAction
    data class OnPlayAllClick(val audioUrls: List<String>) : PlayerAction
    data class OnSelectPlayer(val playerComponent: PlayerComponent) : PlayerAction
    data object OnCollapseClick : PlayerAction
    data object OnForwardClick : PlayerAction
    data object OnRewindClick : PlayerAction
    data object OnPauseResumeClick : PlayerAction
}
package org.tawhid.readout.core.player.presentation

sealed interface PlayerAction {
    data class OnPlayClick(val audioUrl: String, val nowPlaying: String) : PlayerAction
    data class OnPlayAudioBase64Click(val audioBase64: String, val nowPlaying: String) : PlayerAction
    data class OnPlayAllClick(val audioUrls: List<String>, val nowPlaying: String) : PlayerAction
    data object OnCollapseClick : PlayerAction
    data object OnForwardClick : PlayerAction
    data object OnRewindClick : PlayerAction
    data object OnPauseResumeClick : PlayerAction
}
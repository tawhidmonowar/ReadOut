package org.tawhid.readout.core.player.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.tawhid.readout.core.player.domain.PlayerRepository

class PlayerViewModel(
    private val repository: PlayerRepository
) : ViewModel() {

    private val _state = MutableStateFlow(PlayerState())
    val state = _state.asStateFlow()

    fun onAction(action: PlayerAction) {
        when (action) {
            is PlayerAction.OnPlayClick -> {
                _state.update {
                    it.copy(
                        isPaused = false,
                        isPlaying = true
                    )
                }
                repository.play(action.audioUrl)
            }

            is PlayerAction.OnPlayAudioBase64Click -> {
                _state.update {
                    it.copy(
                        isPlaying = true,
                        isPaused = false
                    )
                }
                repository.playAudioBase64(action.audioBase64)
            }

            is PlayerAction.OnPlayAllClick -> {
                _state.update {
                    it.copy(
                        isPlaying = true,
                        isPaused = false
                    )
                }
                repository.playAll(action.audioUrls)
            }

            is PlayerAction.OnForwardClick -> {
                repository.forward()
            }

            is PlayerAction.OnRewindClick -> {
                repository.rewind()
            }

            is PlayerAction.OnPauseResumeClick -> {
                _state.update {
                    it.copy(
                        isPaused = !it.isPaused
                    )
                }
                repository.pauseResume()
            }

            is PlayerAction.OnCollapseClick -> {
                _state.update {
                    it.copy(
                        isCollapsed = !it.isCollapsed,
                    )
                }
            }
        }
    }
}
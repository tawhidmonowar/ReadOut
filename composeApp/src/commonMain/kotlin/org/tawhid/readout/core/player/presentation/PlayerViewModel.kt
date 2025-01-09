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
                        isPlaying = true
                    )
                }
                repository.play(action.audioUrl)
            }

            is PlayerAction.OnPlayAudioBase64Click -> {
                _state.update {
                    it.copy(
                        isPlaying = true
                    )
                }
                repository.playAudioBase64(action.audioBase64)
            }

            is PlayerAction.OnPlayAllClick -> {
                _state.update {
                    it.copy(
                        isPlaying = true
                    )
                }
                repository.playAll(action.audioUrls)
            }

            is PlayerAction.OnSelectPlayer -> {
                _state.update {
                    it.copy(
                        selectedPlayerComponent = action.playerComponent
                    )
                }
            }

            is PlayerAction.OnForwardClick -> {
                repository.forward()
            }

            is PlayerAction.OnRewindClick -> {
                repository.rewind()
            }

            is PlayerAction.OnPauseResumeClick -> {
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
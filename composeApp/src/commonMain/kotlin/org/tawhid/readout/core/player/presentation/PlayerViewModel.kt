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

            is PlayerAction.OnSelectPlayer -> {
                _state.update {
                    it.copy(
                        selectedPlayer = action.player
                    )
                }
            }

            is PlayerAction.OnPauseClick -> {
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
package org.tawhid.readout.core.player.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import org.koin.compose.viewmodel.koinViewModel
import org.tawhid.readout.core.player.presentation.PlayerAction
import org.tawhid.readout.core.player.presentation.PlayerViewModel

@Composable
fun PlayingOverlay(
    onPlayerClick: () -> Unit
) {
    val playerViewModel = koinViewModel<PlayerViewModel>()
    val playerState by playerViewModel.state.collectAsState()

    if (playerState.isPlaying) {
        if (playerState.isCollapsed) {
            CollapsedView(
                state = playerState,
                onAction = { playerAction ->
                    when (playerAction) {
                        is PlayerAction.OnCollapseClick -> playerViewModel.onAction(playerAction)
                        else -> Unit
                    }
                }
            )
        } else {
            FullWidthView(
                state = playerState,
                onAction = { playerAction ->
                    when (playerAction) {
                        is PlayerAction.OnPauseClick -> playerViewModel.onAction(playerAction)
                        is PlayerAction.OnCollapseClick -> playerViewModel.onAction(playerAction)
                        else -> Unit
                    }
                },
                modifier = Modifier.clickable { onPlayerClick() }
            )
        }
    }
}
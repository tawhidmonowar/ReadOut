package org.tawhid.readout.core.player.presentation.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import org.jetbrains.compose.resources.painterResource
import org.tawhid.readout.core.player.presentation.PlayerAction
import org.tawhid.readout.core.player.presentation.PlayerState
import org.tawhid.readout.core.theme.Shapes
import org.tawhid.readout.core.theme.extraSmall
import org.tawhid.readout.core.theme.extraThin
import org.tawhid.readout.core.utils.WindowSizes
import readout.composeapp.generated.resources.Res
import readout.composeapp.generated.resources.ic_forward_10
import readout.composeapp.generated.resources.ic_pause
import readout.composeapp.generated.resources.ic_play
import readout.composeapp.generated.resources.ic_rewind_10
import readout.composeapp.generated.resources.player_broken_img

@Composable
fun FullWidthView(
    state: PlayerState,
    windowSize: WindowSizes,
    onAction: (PlayerAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .animateContentSize()
            .padding(start = extraSmall, end = extraSmall, bottom = extraSmall)
            .height(70.dp)
            .then(if (windowSize.isCompactScreen) Modifier.clip(Shapes.small) else Modifier.clip(Shapes.medium))
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = {
                        onAction(PlayerAction.OnCollapseClick)
                    }
                )
            }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth().padding(horizontal = extraSmall)
        ) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .padding(5.dp)
                    .clip(Shapes.small)
                    .background(Color.White)
                    .padding(1.dp),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    modifier = Modifier.size(40.dp).clip(Shapes.small),
                    model = state.imageUrl,
                    error = painterResource(Res.drawable.player_broken_img),
                    contentScale = ContentScale.Crop,
                    contentDescription = null
                )
            }

            Column(
                modifier = Modifier
                    .weight(0.4f)
            ) {
                Text(
                    text = "Now Playing",
                    maxLines = 1,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    modifier = Modifier.basicMarquee(
                        initialDelayMillis = 0,
                        iterations = Int.MAX_VALUE,
                    ),
                    text = "Now Playing: ${state.nowPlaying}",
                    maxLines = 1,
                    color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.8f)
                )
            }


            Row(
                modifier = Modifier.fillMaxHeight().weight(0.6f).padding(end = extraSmall),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(
                    modifier = Modifier.clip(RoundedCornerShape(100))
                        .background(MaterialTheme.colorScheme.primaryContainer)
                        .size(50.dp),
                    onClick = {
                        onAction(PlayerAction.OnRewindClick)
                    }
                ) {
                    Icon(
                        modifier = Modifier.size(30.dp),
                        painter = painterResource(Res.drawable.ic_rewind_10),
                        contentDescription = "Collapse",
                        tint = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }

                Spacer(modifier = Modifier.width(2.dp))

                IconButton(
                    modifier = Modifier.padding(5.dp).clip(RoundedCornerShape(100))
                        .background(MaterialTheme.colorScheme.primaryContainer)
                        .size(50.dp),
                    onClick = {
                        onAction(PlayerAction.OnPauseResumeClick)
                    }
                ) {
                    Icon(
                        modifier = Modifier.size(30.dp),
                        painter = if (state.isPaused) {
                            painterResource(Res.drawable.ic_pause)
                        } else {
                            painterResource(Res.drawable.ic_play)
                        },
                        contentDescription = "Pause or Play",
                        tint = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }

                Spacer(modifier = Modifier.width(2.dp))

                IconButton(
                    modifier = Modifier.clip(RoundedCornerShape(100))
                        .background(MaterialTheme.colorScheme.primaryContainer)
                        .size(50.dp),
                    onClick = {
                        onAction(PlayerAction.OnForwardClick)
                    }
                ) {
                    Icon(
                        modifier = Modifier.size(30.dp),
                        painter = painterResource(Res.drawable.ic_forward_10),
                        contentDescription = "Collapse",
                        tint = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }
        }
    }
}
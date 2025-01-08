package org.tawhid.readout.core.player.presentation.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.tawhid.readout.core.player.presentation.PlayerAction
import org.tawhid.readout.core.player.presentation.PlayerState
import org.tawhid.readout.core.theme.Shapes


@Composable
fun FullWidthView(
    state: PlayerState,
    onAction: (PlayerAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val imgUrl = state.selectedPlayer?.imgUrl
    val title = state.selectedPlayer?.title

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxWidth()
            .animateContentSize()
            .height(80.dp)
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(Shapes.small)
                    .background(Color.White)
                    .padding(1.dp)
            ) {
                /*AsyncImage(
                    modifier = Modifier.size(imageSize).clip(Shapes.small),
                    model = imgUrl,
                    error = painterResource(Res.drawable.broken_image_radio),
                    contentScale = ContentScale.Crop,
                    contentDescription = null
                )*/
            }

            Column(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .weight(1f)
            ) {
                Text(
                    text = title ?: "No Title!",
                    maxLines = 1,
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    modifier = Modifier.basicMarquee(
                        initialDelayMillis = 0,
                        iterations = Int.MAX_VALUE,
                    ),
                    text = "Now Playing: " + (state.nowPlaying ?: "Unknown!"),
                    maxLines = 1,
                    color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f)
                )
            }

            IconButton(onClick = {
                onAction(PlayerAction.OnPauseClick)
            }
            ) {
                Icon(
                    modifier = Modifier.clip(RoundedCornerShape(100))
                        .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f))
                        .padding(6.dp).size(50.dp),
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Pause or Play",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }

            IconButton(
                onClick = {

                }
            ) {
                Icon(
                    modifier = Modifier.clip(RoundedCornerShape(100))
                        .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f))
                        .padding(5.dp).size(50.dp),
                    imageVector = Icons.Default.ThumbUp,
                    contentDescription = "Collapse",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }

            IconButton(
                onClick = {
                    onAction(PlayerAction.OnCollapseClick)
                }
            ) {
                Icon(
                    modifier = Modifier.clip(RoundedCornerShape(100))
                        .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f))
                        .padding(5.dp).size(50.dp),
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = "Collapse",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}
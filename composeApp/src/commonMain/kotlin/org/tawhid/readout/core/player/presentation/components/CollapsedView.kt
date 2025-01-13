package org.tawhid.readout.core.player.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.tawhid.readout.core.player.presentation.PlayerAction
import org.tawhid.readout.core.player.presentation.PlayerState
import readout.composeapp.generated.resources.Res
import readout.composeapp.generated.resources.ic_open_in_full

@Composable
fun CollapsedView(
    onAction: (PlayerAction) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.End
    ) {
        IconButton(
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.secondaryContainer)
                .border(2.dp, MaterialTheme.colorScheme.surfaceContainer, CircleShape),
            onClick = { onAction(PlayerAction.OnCollapseClick) }
        ) {
            Icon(
                painter = painterResource(Res.drawable.ic_open_in_full),
                contentDescription = "Expand",
                tint = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
}
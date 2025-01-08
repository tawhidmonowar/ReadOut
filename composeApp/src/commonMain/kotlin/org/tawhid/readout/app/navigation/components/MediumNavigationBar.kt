package org.tawhid.readout.app.navigation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.tawhid.readout.app.navigation.Route
import org.tawhid.readout.core.theme.extraThin
import org.tawhid.readout.core.theme.mediumNavigationBarWidth
import org.tawhid.readout.core.theme.small

@Composable
fun MediumNavigationBar(
    modifier: Modifier = Modifier,
    items: List<NavigationItem>,
    currentRoute: Route?,
    onItemClick: (NavigationItem) -> Unit
) {
    Row(modifier = modifier) {
        NavigationRail(
            modifier = Modifier.fillMaxHeight().width(mediumNavigationBarWidth),
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        ) {
            items.forEach { navigationItem ->
                val isSelected = when (navigationItem.route) {
                    Route.OpenLibraryGraph -> currentRoute in listOf(Route.OpenLibraryGraph, Route.OpenLibraryDetail())
                    Route.AudioBookGraph -> currentRoute in listOf(Route.AudioBookGraph, Route.AudioBookDetail())
                    else -> navigationItem.route == currentRoute
                }
                val iconPainter = painterResource(
                    if (isSelected) navigationItem.selectedIcon else navigationItem.unSelectedIcon
                )
                NavigationRailItem(
                    modifier = Modifier.padding(vertical = small),
                    selected = isSelected,
                    onClick = { onItemClick(navigationItem) },
                    icon = {
                        Icon(
                            painter = iconPainter,
                            contentDescription = stringResource(navigationItem.title)
                        )
                    },
                    label = {
                        Text(
                            text = stringResource(navigationItem.title),
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1
                        )
                    }
                )
            }
        }
        VerticalDivider(
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
            thickness = extraThin,
            modifier = Modifier.fillMaxHeight()
        )
    }
}
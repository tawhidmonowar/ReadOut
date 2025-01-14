package org.tawhid.readout.app.navigation.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.style.TextOverflow
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.tawhid.readout.app.navigation.Route

@Composable
fun CompactNavigationBar(
    currentRoute: Route?,
    items: List<NavigationItem>,
    onItemClick: (NavigationItem) -> Unit
) {
    NavigationBar(
        modifier = Modifier.fillMaxWidth(),
    ) {
        items.forEach { navigationItem ->
            val isSelected = when (navigationItem.route) {
                Route.OpenLibraryGraph -> currentRoute in listOf(Route.OpenLibraryGraph, Route.OpenLibraryDetail())
                Route.AudioBookGraph -> currentRoute in listOf(Route.AudioBookGraph, Route.AudioBookDetail())
                Route.Home -> currentRoute in listOf(Route.Home, Route.RecentRelease)
                else -> navigationItem.route == currentRoute
            }
            NavigationBarItem(
                selected = isSelected,
                onClick = { onItemClick(navigationItem) },
                icon = {
                    Icon(
                        painter = if (navigationItem.route == currentRoute) {
                            painterResource(navigationItem.selectedIcon)
                        } else {
                            painterResource(navigationItem.unSelectedIcon)
                        },
                        contentDescription = stringResource(navigationItem.title),
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
}
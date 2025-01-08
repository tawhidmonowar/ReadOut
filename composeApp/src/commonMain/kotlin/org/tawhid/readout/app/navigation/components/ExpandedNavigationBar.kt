package org.tawhid.readout.app.navigation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.tawhid.readout.app.navigation.Route
import org.tawhid.readout.core.theme.Shapes
import org.tawhid.readout.core.theme.expandedNavigationBarWidth
import org.tawhid.readout.core.theme.extraThin
import org.tawhid.readout.core.theme.small
import org.tawhid.readout.core.theme.thin

@Composable
fun ExpandedNavigationBar(
    modifier: Modifier = Modifier,
    items: List<NavigationItem>,
    currentRoute: Route?,
    onItemClick: (NavigationItem) -> Unit
) {
    Row(modifier = modifier) {
        NavigationRail(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
            modifier = Modifier.fillMaxHeight().width(expandedNavigationBarWidth),
            header = { Spacer(modifier = Modifier.height(thin)) }
        ) {
            items.forEach { navigationItem ->
                val isSelected = when (navigationItem.route) {
                    Route.OpenLibraryGraph -> currentRoute in listOf(Route.OpenLibraryGraph, Route.OpenLibraryDetail())
                    Route.AudioBookGraph -> currentRoute in listOf(Route.AudioBookGraph, Route.AudioBookDetail())
                    else -> navigationItem.route == currentRoute
                }
                if (navigationItem == items.last()) {
                    Spacer(modifier = Modifier.height(thin))
                    HorizontalDivider(
                        thickness = extraThin,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
                    )
                    Spacer(modifier = Modifier.height(thin))
                }
                NavigationItemView(
                    navigationItem = navigationItem,
                    isSelected = isSelected,
                    onItemClick = { onItemClick(navigationItem) }
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

@Composable
private fun NavigationItemView(
    navigationItem: NavigationItem,
    isSelected: Boolean,
    onItemClick: () -> Unit
) {
    NavigationDrawerItem(
        modifier = Modifier.padding(horizontal = small),
        shape = Shapes.small,
        selected = isSelected,
        onClick = onItemClick,
        icon = {
            Icon(
                painter = painterResource(
                    if (isSelected) navigationItem.selectedIcon else navigationItem.unSelectedIcon
                ),
                contentDescription = stringResource(navigationItem.title),
            )
        },
        label = {
            Text(
                text = stringResource(navigationItem.title),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                style = MaterialTheme.typography.titleSmall
            )
        }
    )
}
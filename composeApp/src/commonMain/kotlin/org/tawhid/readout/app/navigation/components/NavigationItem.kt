package org.tawhid.readout.app.navigation.components

import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.tawhid.readout.app.navigation.Route

data class NavigationItem(
    val unSelectedIcon: DrawableResource,
    val selectedIcon: DrawableResource,
    val title: StringResource,
    val route: Route
)
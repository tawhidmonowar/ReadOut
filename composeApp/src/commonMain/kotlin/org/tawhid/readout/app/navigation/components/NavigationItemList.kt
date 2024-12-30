package org.tawhid.readout.app.navigation.components

import org.tawhid.readout.app.navigation.Route
import readout.composeapp.generated.resources.Res
import readout.composeapp.generated.resources.audiobooks
import readout.composeapp.generated.resources.home
import readout.composeapp.generated.resources.ic_home_filled
import readout.composeapp.generated.resources.ic_home_outlined
import readout.composeapp.generated.resources.ic_library_filled
import readout.composeapp.generated.resources.ic_library_music_filled
import readout.composeapp.generated.resources.ic_library_music_outlined
import readout.composeapp.generated.resources.ic_library_outlined
import readout.composeapp.generated.resources.ic_settings_filled
import readout.composeapp.generated.resources.open_library
import readout.composeapp.generated.resources.setting

val navigationItemsLists = listOf(
    NavigationItem(
        unSelectedIcon = Res.drawable.ic_home_outlined,
        selectedIcon = Res.drawable.ic_home_filled,
        title = Res.string.home,
        route = Route.Home
    ),
    NavigationItem(
        unSelectedIcon = Res.drawable.ic_library_outlined,
        selectedIcon = Res.drawable.ic_library_filled,
        title = Res.string.open_library,
        route = Route.OpenLibrary
    ),
    NavigationItem(
        unSelectedIcon = Res.drawable.ic_library_music_outlined,
        selectedIcon = Res.drawable.ic_library_music_filled,
        title = Res.string.audiobooks,
        route = Route.AudioBooks
    )
)

val settingNavigationItems = NavigationItem(
    unSelectedIcon = Res.drawable.ic_settings_filled,
    selectedIcon = Res.drawable.ic_settings_filled,
    title = Res.string.setting,
    route = Route.Setting
)
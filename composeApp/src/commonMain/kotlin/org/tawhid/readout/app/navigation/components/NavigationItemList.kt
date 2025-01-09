package org.tawhid.readout.app.navigation.components

import org.tawhid.readout.app.navigation.Route
import readout.composeapp.generated.resources.Res
import readout.composeapp.generated.resources.audiobooks
import readout.composeapp.generated.resources.home
import readout.composeapp.generated.resources.ic_audiobooks_filled
import readout.composeapp.generated.resources.ic_audiobooks_outlined
import readout.composeapp.generated.resources.ic_auto_awesome_filled
import readout.composeapp.generated.resources.ic_auto_awesome_outlined
import readout.composeapp.generated.resources.ic_home_filled
import readout.composeapp.generated.resources.ic_home_outlined
import readout.composeapp.generated.resources.ic_library_filled
import readout.composeapp.generated.resources.ic_library_music_filled
import readout.composeapp.generated.resources.ic_library_music_outlined
import readout.composeapp.generated.resources.ic_library_outlined
import readout.composeapp.generated.resources.ic_settings_filled
import readout.composeapp.generated.resources.ic_settings_outlined
import readout.composeapp.generated.resources.open_library
import readout.composeapp.generated.resources.setting
import readout.composeapp.generated.resources.summarize

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
        route = Route.OpenLibraryGraph
    ),
    NavigationItem(
        unSelectedIcon = Res.drawable.ic_audiobooks_outlined,
        selectedIcon = Res.drawable.ic_audiobooks_filled,
        title = Res.string.audiobooks,
        route = Route.AudioBookGraph
    )
)

val settingNavigationItem = NavigationItem(
    unSelectedIcon = Res.drawable.ic_settings_outlined,
    selectedIcon = Res.drawable.ic_settings_filled,
    title = Res.string.setting,
    route = Route.Setting
)

val summarizeNavigationItem = NavigationItem(
    unSelectedIcon = Res.drawable.ic_auto_awesome_outlined,
    selectedIcon = Res.drawable.ic_auto_awesome_filled,
    title = Res.string.summarize,
    route = Route.Summarize
)
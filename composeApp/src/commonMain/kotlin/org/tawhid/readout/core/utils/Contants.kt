package org.tawhid.readout.core.utils

import org.jetbrains.compose.resources.StringResource
import readout.composeapp.generated.resources.Res
import readout.composeapp.generated.resources.dark_mode
import readout.composeapp.generated.resources.light_mode
import readout.composeapp.generated.resources.system_default

enum class DeviceType {
    Mobile, Desktop
}

sealed class WindowSize {
    data object Compact : WindowSize()
    data object Medium : WindowSize()
    data object Expanded : WindowSize()
}

enum class Theme(val title: StringResource) {
    SYSTEM_DEFAULT(Res.string.system_default),
    LIGHT_MODE(Res.string.light_mode),
    DARK_MODE(Res.string.dark_mode)
}
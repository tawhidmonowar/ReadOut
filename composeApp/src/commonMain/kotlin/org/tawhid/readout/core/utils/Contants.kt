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

data class WindowSizes(
    val isExpandedScreen: Boolean,
    val isMediumScreen: Boolean,
    val isCompactScreen: Boolean
)

enum class Theme(val title: StringResource) {
    SYSTEM_DEFAULT(Res.string.system_default),
    LIGHT_MODE(Res.string.light_mode),
    DARK_MODE(Res.string.dark_mode)
}

const val DATA_STORE_FILE_NAME = "setting.preferences_pb"

val openLibrary_book_subject = listOf(
    "Arts",
    "Science Fiction",
    "Biographies",
    "Children",
    "History",
    "Medicine",
    "Religion",
    "Science"
)
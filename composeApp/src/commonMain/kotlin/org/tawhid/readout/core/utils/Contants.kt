package org.tawhid.readout.core.utils

enum class DeviceType {
    Mobile, Desktop
}

sealed class WindowSize {
    data object Compact : WindowSize()
    data object Medium : WindowSize()
    data object Expanded : WindowSize()
}
package org.tawhid.readout.core.utils

import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable

actual fun getDeviceType(): DeviceType { return DeviceType.Desktop }

@Composable
@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
actual fun calculateWindowSize(): WindowSize {
    val windowWidthClass = calculateWindowSizeClass().widthSizeClass
    return when (windowWidthClass) {
        WindowWidthSizeClass.Expanded -> { WindowSize.Expanded }
        WindowWidthSizeClass.Medium -> { WindowSize.Medium }
        else -> { WindowSize.Compact }
    }
}
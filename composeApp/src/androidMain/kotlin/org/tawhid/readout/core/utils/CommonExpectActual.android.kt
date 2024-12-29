package org.tawhid.readout.core.utils

import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.window.core.layout.WindowWidthSizeClass

actual fun getDeviceType(): DeviceType { return DeviceType.Mobile }

@Composable
actual fun calculateWindowSize(): WindowSize {
    val windowWidthClass = currentWindowAdaptiveInfo().windowSizeClass.windowWidthSizeClass
    return when (windowWidthClass) {
        WindowWidthSizeClass.EXPANDED -> { WindowSize.Expanded }
        WindowWidthSizeClass.MEDIUM -> { WindowSize.Medium }
        else -> { WindowSize.Compact }
    }
}
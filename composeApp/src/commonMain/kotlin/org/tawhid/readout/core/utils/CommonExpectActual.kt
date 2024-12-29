package org.tawhid.readout.core.utils

import androidx.compose.runtime.Composable

expect fun getDeviceType(): DeviceType
@Composable
expect fun calculateWindowSize(): WindowSize
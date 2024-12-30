package org.tawhid.readout.core.setting

import org.tawhid.readout.core.utils.Theme

data class SettingState(
    val currentTheme: String? = Theme.SYSTEM_DEFAULT.name,
    val showClearDataDialog: Boolean = false,
    val showThemeDialog: Boolean = false
)
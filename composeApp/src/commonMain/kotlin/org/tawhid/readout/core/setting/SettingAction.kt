package org.tawhid.readout.core.setting

sealed interface SettingAction {
    data object OnBackClick : SettingAction
    data object ShowThemeDialog : SettingAction
    data object HideThemeDialog : SettingAction
    data object ShowClearDataDialog : SettingAction
    data object HideClearDataDialog : SettingAction
    data class ChangeTheme(val theme: String) : SettingAction
}
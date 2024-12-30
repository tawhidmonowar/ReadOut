package org.tawhid.readout.core.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.tawhid.readout.core.utils.AppPreferences

class SettingViewModel(
    private val appPreferences: AppPreferences,
) : ViewModel() {

    private val _state = MutableStateFlow(
        SettingState(
            currentTheme = appPreferences.getThemeSync()
        )
    )

    val state: StateFlow<SettingState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val currentTheme = appPreferences.getTheme()
            _state.update { it.copy(currentTheme = currentTheme) }
        }
    }

    fun onAction(action: SettingAction) {
        when (action) {
            is SettingAction.ChangeTheme -> {
                viewModelScope.launch(Dispatchers.IO) {
                    appPreferences.changeThemeMode(action.theme)
                    _state.update { it.copy(currentTheme = action.theme, showThemeDialog = false) }
                }
            }

            is SettingAction.ShowThemeDialog -> {
                _state.update { it.copy(showThemeDialog = true) }
            }

            is SettingAction.HideThemeDialog -> {
                _state.update { it.copy(showThemeDialog = false) }
            }

            is SettingAction.ShowClearDataDialog -> {
                _state.update { it.copy(showClearDataDialog = true) }
            }

            is SettingAction.HideClearDataDialog -> {
                _state.update { it.copy(showClearDataDialog = false) }
            }

            else -> Unit
        }
    }
}
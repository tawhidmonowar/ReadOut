package org.tawhid.readout.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext
import org.koin.compose.viewmodel.koinViewModel
import org.tawhid.readout.app.navigation.NavigationScreenRoot
import org.tawhid.readout.core.setting.SettingViewModel
import org.tawhid.readout.core.theme.ReadOutTheme
import org.tawhid.readout.core.utils.Theme

@Composable
@Preview
fun App() {
    KoinContext {
        val settingViewModel = koinViewModel<SettingViewModel>()
        val settingState by settingViewModel.state.collectAsState()
        val currentTheme = settingState.currentTheme
        ReadOutTheme(currentTheme) {
            NavigationScreenRoot(settingViewModel)
        }
    }
}
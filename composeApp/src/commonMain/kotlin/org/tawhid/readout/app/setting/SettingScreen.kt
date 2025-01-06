package org.tawhid.readout.app.setting

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.tawhid.readout.app.setting.components.ClearDataDialog
import org.tawhid.readout.app.setting.components.SettingItem
import org.tawhid.readout.app.setting.components.ThemeSelectionDialog
import org.tawhid.readout.core.utils.Theme
import readout.composeapp.generated.resources.Res
import readout.composeapp.generated.resources.clear_data
import readout.composeapp.generated.resources.go_back
import readout.composeapp.generated.resources.setting
import readout.composeapp.generated.resources.theme

@Composable
fun SettingScreenRoot(
    viewModel: SettingViewModel = koinViewModel(),
    innerPadding: PaddingValues,
    onBackClick: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    SettingScreen(
        state = state,
        innerPadding = innerPadding,
        onAction = { action ->
            when (action) {
                is SettingAction.OnBackClick -> onBackClick()
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SettingScreen(
    state: SettingState,
    innerPadding: PaddingValues,
    onAction: (SettingAction) -> Unit
) {
    when {
        state.showThemeDialog -> {
            ThemeSelectionDialog(
                currentTheme = state.currentTheme ?: Theme.SYSTEM_DEFAULT.name,
                onThemeChange = { theme ->
                    onAction(SettingAction.ChangeTheme(theme.name))
                    onAction(SettingAction.HideThemeDialog)
                },
                onDismissRequest = {
                    onAction(SettingAction.HideThemeDialog)
                }
            )
        }

        state.showClearDataDialog -> {
            ClearDataDialog(
                onDismissRequest = {
                    onAction(SettingAction.HideClearDataDialog)
                },
                onDeleteHistory = {
                    onAction(SettingAction.HideClearDataDialog)
                }
            )
        }
    }

    Scaffold(
        modifier = Modifier.padding(innerPadding),
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(Res.string.setting))
                },
                navigationIcon = {
                    IconButton(onClick = {
                        onAction(SettingAction.OnBackClick)
                    }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            stringResource(Res.string.go_back)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(paddingValues)
        ) {
            item {
                SettingItem(
                    onClick = {
                        onAction(SettingAction.ShowThemeDialog)
                    },
                    painter = Icons.Filled.Settings,
                    itemName = stringResource(Res.string.theme)
                )
            }

            item {
                SettingItem(
                    onClick = {
                        onAction(SettingAction.ShowClearDataDialog)
                    },
                    painter = Icons.Filled.Delete,
                    itemName = stringResource(Res.string.clear_data)
                )
            }
        }
    }
}

package org.tawhid.readout.app.setting.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.stringResource
import org.tawhid.readout.core.theme.extraSmall
import org.tawhid.readout.core.theme.large
import org.tawhid.readout.core.theme.medium
import org.tawhid.readout.core.theme.small
import org.tawhid.readout.core.utils.Theme
import readout.composeapp.generated.resources.Res
import readout.composeapp.generated.resources.apply
import readout.composeapp.generated.resources.cancel
import readout.composeapp.generated.resources.choose_a_theme
import readout.composeapp.generated.resources.clear_data_title
import readout.composeapp.generated.resources.clear_data_warning
import readout.composeapp.generated.resources.delete

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClearDataDialog(
    onDeleteHistory: () -> Unit,
    onDismissRequest: () -> Unit
) {
    BasicAlertDialog(onDismissRequest = onDismissRequest, content = {
        Surface(
            modifier = Modifier.wrapContentWidth().wrapContentHeight(),
            shape = MaterialTheme.shapes.large,
            tonalElevation = AlertDialogDefaults.TonalElevation
        ) {
            Column(modifier = Modifier.padding(medium)) {
                Text(
                    text = stringResource(Res.string.clear_data_title),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(small)
                )

                Spacer(modifier = Modifier.height(extraSmall))

                Text(
                    text = stringResource(Res.string.clear_data_warning),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(small)
                )

                Spacer(modifier = Modifier.height(large))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    TextButton(onClick = onDismissRequest) {
                        Text(stringResource(Res.string.cancel))
                    }
                    Spacer(modifier = Modifier.width(medium))
                    TextButton(onClick = { onDeleteHistory() }) {
                        Text(stringResource(Res.string.delete))
                    }
                }
            }
        }
    })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThemeSelectionDialog(
    onThemeChange: (Theme) -> Unit,
    onDismissRequest: () -> Unit,
    currentTheme: String
) {
    var currentSelectedTheme by remember { mutableStateOf(Theme.valueOf(currentTheme)) }

    BasicAlertDialog(onDismissRequest = onDismissRequest, content = {
        Surface(
            modifier = Modifier.wrapContentWidth().wrapContentHeight(),
            shape = MaterialTheme.shapes.large,
            tonalElevation = AlertDialogDefaults.TonalElevation
        ) {
            Column(modifier = Modifier.padding(medium)) {
                Text(
                    text = stringResource(Res.string.choose_a_theme),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(small)
                )
                Theme.entries.forEach { theme ->
                    Row(
                        modifier = Modifier.fillMaxWidth()
                            .clickable { currentSelectedTheme = theme },
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = currentSelectedTheme == theme,
                            onClick = { currentSelectedTheme = theme },
                            colors = RadioButtonDefaults.colors(selectedColor = MaterialTheme.colorScheme.primary)
                        )
                        Text(text = stringResource(theme.title))
                    }
                }

                Spacer(modifier = Modifier.height(large))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    TextButton(onClick = onDismissRequest) {
                        Text(stringResource(Res.string.cancel))
                    }
                    Spacer(modifier = Modifier.width(medium))
                    TextButton(onClick = { onThemeChange(currentSelectedTheme) }) {
                        Text(stringResource(Res.string.apply))
                    }
                }
            }
        }
    })
}
package org.tawhid.readout.app

import androidx.compose.runtime.Composable
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.tawhid.readout.app.navigation.NavigationScreenRoot
import org.tawhid.readout.core.theme.ReadOutTheme
import org.tawhid.readout.core.utils.Theme

@Composable
@Preview
fun App() {
    ReadOutTheme(Theme.DARK_MODE.name) {
        NavigationScreenRoot()
    }
}
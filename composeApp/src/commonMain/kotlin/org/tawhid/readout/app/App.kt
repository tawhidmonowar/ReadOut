package org.tawhid.readout.app

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.tawhid.readout.app.navigation.NavigationScreenRoot

@Composable
@Preview
fun App() {
    MaterialTheme {
        NavigationScreenRoot()
    }
}
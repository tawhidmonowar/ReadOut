package org.tawhid.readout.app.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Text
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.tawhid.readout.core.setting.SettingScreenRoot
import org.tawhid.readout.core.setting.SettingViewModel


fun NavGraphBuilder.navGraphBuilder(
    settingViewModel: SettingViewModel,
    rootNavController: NavController,
    innerPadding: PaddingValues
) {
    composable<Route.Home> {
        Text(text = "Home")
    }

    composable<Route.OpenLibrary> {
        Text(text = "OpenLibrary")
    }

    composable<Route.AudioBooks> {
        Text(text = "Audio Books")
    }

    composable<Route.Setting> {
        SettingScreenRoot(
            viewModel = settingViewModel,
            innerPadding = innerPadding,
            onBackClick = {
                rootNavController.popBackStack()
            }
        )
    }

}


/*
@Composable
private inline fun <reified T : ViewModel> NavBackStackEntry.sharedKoinViewModel(
    navController: NavController
): T {
    val navGraphRoute = destination.parent?.route ?: return koinViewModel<T>()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return koinViewModel(
        viewModelStoreOwner = parentEntry
    )
}*/

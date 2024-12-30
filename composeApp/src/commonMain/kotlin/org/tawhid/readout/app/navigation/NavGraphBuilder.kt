package org.tawhid.readout.app.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Text
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable


fun NavGraphBuilder.navGraphBuilder(
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
        Text(text = "Setting")
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

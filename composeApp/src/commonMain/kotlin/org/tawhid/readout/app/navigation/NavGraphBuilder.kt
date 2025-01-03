package org.tawhid.readout.app.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import org.koin.compose.viewmodel.koinViewModel
import org.tawhid.readout.book.openbook.presentation.SharedBookViewModel
import org.tawhid.readout.book.openbook.presentation.openbook_detail.BookDetailAction
import org.tawhid.readout.book.openbook.presentation.openbook_detail.BookDetailScreenRoot
import org.tawhid.readout.book.openbook.presentation.openbook_detail.BookDetailViewModel
import org.tawhid.readout.book.openbook.presentation.openbook_home.BookHomeScreenRoot
import org.tawhid.readout.book.openbook.presentation.openbook_home.BookHomeViewModel
import org.tawhid.readout.core.setting.SettingScreenRoot
import org.tawhid.readout.core.setting.SettingViewModel
import org.tawhid.readout.core.utils.WindowSizes

fun NavGraphBuilder.navGraphBuilder(
    rootNavController: NavController,
    settingViewModel: SettingViewModel,
    windowSize: WindowSizes,
    innerPadding: PaddingValues
) {
    composable<Route.Home> {
        Text(text = "Home")
    }

    composable<Route.AudioBooks> {
        Text(text = "Audio Books")
    }

    composable<Route.Setting> {
        SettingScreenRoot(
            viewModel = settingViewModel,
            innerPadding = innerPadding,
            onBackClick = {
                rootNavController.navigateUp()
            }
        )
    }

    composable<Route.Summarize> {
        Text(text = "Summarize")
    }



    navigation<Route.OpenLibraryGraph>(
        startDestination = Route.OpenLibrary
    ) {
        composable<Route.OpenLibrary> {
            val bookHomeViewModel = koinViewModel<BookHomeViewModel>()
            val sharedBookViewModel = it.sharedKoinViewModel<SharedBookViewModel>(rootNavController)
            LaunchedEffect(true) { sharedBookViewModel.onSelectBook(null) }
            BookHomeScreenRoot(
                viewModel = bookHomeViewModel,
                windowSize = windowSize,
                innerPadding = innerPadding,
                onBookClick = { book ->
                    sharedBookViewModel.onSelectBook(book)
                    rootNavController.navigate(
                        Route.OpenLibraryDetail(book.id)
                    )
                },
                onSettingClick = {
                    rootNavController.navigate(Route.Setting)
                }
            )
        }
        composable<Route.OpenLibraryDetail> { it ->
            val bookDetailViewModel = koinViewModel<BookDetailViewModel>()
            val sharedBookViewModel = it.sharedKoinViewModel<SharedBookViewModel>(rootNavController)
            val selectedBook by sharedBookViewModel.selectedBook.collectAsStateWithLifecycle()
            LaunchedEffect(selectedBook) {
                selectedBook?.let {
                    bookDetailViewModel.onAction(BookDetailAction.OnSelectedBookChange(it))
                }
            }
            BookDetailScreenRoot(
                viewModel = bookDetailViewModel,
                onBackClick = {
                    rootNavController.navigateUp()
                }
            )
        }
    }
}



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
}

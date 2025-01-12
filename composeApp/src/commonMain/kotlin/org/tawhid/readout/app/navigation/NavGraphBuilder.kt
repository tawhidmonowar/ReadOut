package org.tawhid.readout.app.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import org.koin.compose.viewmodel.koinViewModel
import org.tawhid.readout.app.home.presentation.HomeScreenRoot
import org.tawhid.readout.app.home.presentation.HomeViewModel
import org.tawhid.readout.app.setting.SettingScreenRoot
import org.tawhid.readout.app.setting.SettingViewModel
import org.tawhid.readout.book.audiobook.presentation.SharedAudioBookViewModel
import org.tawhid.readout.book.audiobook.presentation.audiobook_detail.AudioBookDetailAction
import org.tawhid.readout.book.audiobook.presentation.audiobook_detail.AudioBookDetailScreenRoot
import org.tawhid.readout.book.audiobook.presentation.audiobook_detail.AudioBookDetailViewModel
import org.tawhid.readout.book.audiobook.presentation.audiobook_home.AudioBookHomeScreenRoot
import org.tawhid.readout.book.audiobook.presentation.audiobook_home.AudioBookHomeViewModel
import org.tawhid.readout.book.openbook.presentation.SharedBookViewModel
import org.tawhid.readout.book.openbook.presentation.openbook_detail.BookDetailAction
import org.tawhid.readout.book.openbook.presentation.openbook_detail.BookDetailScreenRoot
import org.tawhid.readout.book.openbook.presentation.openbook_detail.BookDetailViewModel
import org.tawhid.readout.book.openbook.presentation.openbook_home.BookHomeScreenRoot
import org.tawhid.readout.book.openbook.presentation.openbook_home.BookHomeViewModel
import org.tawhid.readout.book.openbook.presentation.openbook_saved.BookSavedScreenRoot
import org.tawhid.readout.book.openbook.presentation.openbook_saved.BookSavedViewModel
import org.tawhid.readout.book.summarize.presentation.summarize_home.SummarizeScreenRoot
import org.tawhid.readout.core.utils.WindowSizes

fun NavGraphBuilder.navGraphBuilder(
    rootNavController: NavController,
    settingViewModel: SettingViewModel,
    windowSize: WindowSizes,
    innerPadding: PaddingValues
) {
    composable<Route.Home> {
        val homeViewModel = koinViewModel<HomeViewModel>()
        HomeScreenRoot(
            viewModel = homeViewModel,
            onSettingClick = {
                rootNavController.navigate(Route.Setting)
            },
            onSummarizeClick = {
                rootNavController.navigate(Route.Summarize)
            },
            windowSize = windowSize,
            innerPadding = innerPadding
        )
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
        SummarizeScreenRoot(
            innerPadding = innerPadding,
            windowSize = windowSize
        )
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
                },
                onViewAllClick = {
                    rootNavController.navigate(Route.BookSavedScreen)
                }
            )
        }

        composable<Route.BookSavedScreen> {
            val bookSavedViewModel = koinViewModel<BookSavedViewModel>()
            val sharedBookViewModel = it.sharedKoinViewModel<SharedBookViewModel>(rootNavController)
            LaunchedEffect(true) { sharedBookViewModel.onSelectBook(null) }
            BookSavedScreenRoot(
                viewModel = bookSavedViewModel,
                windowSize = windowSize,
                innerPadding = innerPadding,
                onBookClick = { book ->
                    sharedBookViewModel.onSelectBook(book)
                    rootNavController.navigate(
                        Route.OpenLibraryDetail(book.id)
                    )
                },
                onBackClick = {
                    rootNavController.navigateUp()
                },
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
                innerPadding = innerPadding,
                windowSize = windowSize,
                onBackClick = {
                    rootNavController.navigateUp()
                }
            )
        }
    }

    navigation<Route.AudioBookGraph>(
        startDestination = Route.AudioBook
    ) {
        composable<Route.AudioBook> {
            val audioBookHomeViewModel = koinViewModel<AudioBookHomeViewModel>()
            val sharedAudioBookViewModel =
                it.sharedKoinViewModel<SharedAudioBookViewModel>(rootNavController)
            LaunchedEffect(true) { sharedAudioBookViewModel.onSelectBook(null) }
            AudioBookHomeScreenRoot(
                viewModel = audioBookHomeViewModel,
                windowSize = windowSize,
                innerPadding = innerPadding,
                onAudioBookClick = { book ->
                    sharedAudioBookViewModel.onSelectBook(book)
                    rootNavController.navigate(
                        Route.AudioBookDetail(book.id)
                    )
                },
                onSettingClick = {
                    rootNavController.navigate(Route.Setting)
                }
            )
        }

        composable<Route.AudioBookDetail> { it ->
            val audioBookDetailViewModel = koinViewModel<AudioBookDetailViewModel>()
            val sharedAudioBookViewModel =
                it.sharedKoinViewModel<SharedAudioBookViewModel>(rootNavController)
            val selectedBook by sharedAudioBookViewModel.selectedBook.collectAsStateWithLifecycle()
            LaunchedEffect(selectedBook) {
                selectedBook?.let {
                    audioBookDetailViewModel.onAction(AudioBookDetailAction.OnSelectedBookChange(it))
                }
            }
            AudioBookDetailScreenRoot(
                viewModel = audioBookDetailViewModel,
                innerPadding = innerPadding,
                windowSize = windowSize,
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

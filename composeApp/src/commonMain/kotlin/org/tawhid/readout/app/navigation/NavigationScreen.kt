package org.tawhid.readout.app.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.koin.compose.viewmodel.koinViewModel
import org.tawhid.readout.app.navigation.components.CompactNavigationBar
import org.tawhid.readout.app.navigation.components.ExpandedNavigationBar
import org.tawhid.readout.app.navigation.components.MediumNavigationBar
import org.tawhid.readout.app.navigation.components.NavigationItem
import org.tawhid.readout.app.navigation.components.navigationItemsLists
import org.tawhid.readout.app.navigation.components.settingNavigationItem
import org.tawhid.readout.app.navigation.components.summarizeNavigationItem
import org.tawhid.readout.app.setting.SettingViewModel
import org.tawhid.readout.core.player.presentation.components.PlayingOverlay
import org.tawhid.readout.core.theme.expandedNavigationBarWidth
import org.tawhid.readout.core.theme.mediumNavigationBarWidth
import org.tawhid.readout.core.utils.WindowSize
import org.tawhid.readout.core.utils.WindowSizes
import org.tawhid.readout.core.utils.calculateWindowSize

@Composable
fun NavigationScreenRoot(
    settingViewModel: SettingViewModel = koinViewModel()
) {
    val calculateWindowSize = calculateWindowSize()
    val isExpandedScreen by remember(calculateWindowSize) { derivedStateOf { calculateWindowSize == WindowSize.Expanded } }
    val isMediumScreen by remember(calculateWindowSize) { derivedStateOf { calculateWindowSize == WindowSize.Medium } }
    val isCompactScreen by remember(calculateWindowSize) { derivedStateOf { calculateWindowSize == WindowSize.Compact } }
    val windowSize = WindowSizes(isExpandedScreen, isMediumScreen, isCompactScreen)

    val navigationItems = if (isExpandedScreen || isMediumScreen) {
        navigationItemsLists + summarizeNavigationItem + settingNavigationItem
    } else {
        navigationItemsLists
    }

    val rootNavController = rememberNavController()
    val rootNavBackStackEntry by rootNavController.currentBackStackEntryAsState()
    val currentRoutePath by remember(rootNavBackStackEntry) {
        derivedStateOf {
            rootNavBackStackEntry?.destination?.route?.substringBefore("?")
                ?: Route.Home::class.qualifiedName.orEmpty()
        }
    }

    val currentRoute = getCurrentRoute(currentRoutePath)
    val navigationBarsVisibleRoutes by derivedStateOf {
        mutableListOf(
            Route.Home,
            Route.AudioBookGraph,
            Route.OpenLibraryGraph,
        ).apply {
            if (!isCompactScreen) {
                add(Route.Setting)
                add(Route.Summarize)
                add(Route.OpenLibraryDetail())
                add(Route.AudioBookDetail())
                add(Route.BookSavedScreen)
            }
        }
    }

    val isNavigationBarsVisible by remember(currentRoute, navigationBarsVisibleRoutes) {
        derivedStateOf { currentRoute in navigationBarsVisibleRoutes }
    }

    val playingOverlayVisibleRoutes by derivedStateOf {
        mutableListOf(
            Route.Home,
            Route.AudioBookGraph,
            Route.OpenLibraryGraph,
            Route.Setting,
            Route.Summarize,
            Route.OpenLibraryDetail(),
            Route.AudioBookDetail(),
            Route.BookSavedScreen
        )
    }

    val isPlayingOverlayVisible by remember(currentRoute, playingOverlayVisibleRoutes) {
        derivedStateOf { currentRoute in playingOverlayVisibleRoutes }
    }

    NavigationScreen(
        rootNavController = rootNavController,
        settingViewModel = settingViewModel,
        windowSize = windowSize,
        currentRoute = currentRoute,
        navigationItems = navigationItems,
        isNavigationBarsVisible = isNavigationBarsVisible,
        isPlayingOverlayVisible = isPlayingOverlayVisible,
        isCompactScreen = isCompactScreen,
        isMediumScreen = isMediumScreen,
        isExpandedScreen = isExpandedScreen
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NavigationScreen(
    modifier: Modifier = Modifier,
    rootNavController: NavHostController,
    settingViewModel: SettingViewModel,
    windowSize: WindowSizes,
    currentRoute: Route?,
    navigationItems: List<NavigationItem>,
    isNavigationBarsVisible: Boolean,
    isPlayingOverlayVisible: Boolean,
    isCompactScreen: Boolean,
    isMediumScreen: Boolean,
    isExpandedScreen: Boolean,
) {
    Scaffold(
        modifier = modifier,
        bottomBar = {
            AnimatedVisibility(
                visible = isCompactScreen && isNavigationBarsVisible,
                enter = fadeIn() + slideInVertically(initialOffsetY = { fullHeight -> fullHeight }),
                exit = fadeOut() + slideOutVertically(targetOffsetY = { fullHeight -> fullHeight })
            ) {
                CompactNavigationBar(
                    items = navigationItems,
                    currentRoute = currentRoute,
                    onItemClick = { currentNavigationItem ->
                        rootNavController.navigate(currentNavigationItem.route) {
                            popUpTo(Route.Home) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { innerPadding ->

        val contentPadding = if (isExpandedScreen) {
            PaddingValues(start = expandedNavigationBarWidth)
        } else if (isMediumScreen) {
            PaddingValues(start = mediumNavigationBarWidth)
        } else {
            innerPadding
        }

        Box(modifier = Modifier.fillMaxSize()) {
            NavHost(
                navController = rootNavController,
                startDestination = Route.Home,
            ) {
                navGraphBuilder(
                    rootNavController = rootNavController,
                    settingViewModel = settingViewModel,
                    windowSize = windowSize,
                    innerPadding = contentPadding
                )
            }

            AnimatedVisibility(
                visible = isNavigationBarsVisible && isMediumScreen,
                enter = slideInHorizontally(initialOffsetX = { fullWidth -> -fullWidth }) + fadeIn(),
                exit = slideOutHorizontally(targetOffsetX = { fullWidth -> -fullWidth }) + fadeOut()
            ) {
                MediumNavigationBar(
                    items = navigationItems,
                    currentRoute = currentRoute,
                    onItemClick = { currentNavigationItem ->
                        rootNavController.navigate(currentNavigationItem.route) {
                            popUpTo(Route.Home) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }

            AnimatedVisibility(
                visible = isNavigationBarsVisible && isExpandedScreen,
                enter = slideInHorizontally(initialOffsetX = { fullWidth -> -fullWidth }) + fadeIn(),
                exit = slideOutHorizontally(targetOffsetX = { fullWidth -> -fullWidth }) + fadeOut()
            ) {
                ExpandedNavigationBar(
                    items = navigationItems,
                    currentRoute = currentRoute,
                    onItemClick = { currentNavigationItem ->
                        rootNavController.navigate(currentNavigationItem.route) {
                            popUpTo(Route.Home) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }

            AnimatedVisibility(
                visible = isPlayingOverlayVisible,
                enter = fadeIn() + slideInVertically(initialOffsetY = { fullHeight -> fullHeight }),
                exit = fadeOut() + slideOutVertically(targetOffsetY = { fullHeight -> fullHeight }),
                modifier = Modifier.align(Alignment.BottomCenter).padding(innerPadding)
            ) {
                PlayingOverlay(
                    onPlayerClick = {

                    }
                )
            }

        }
    }
}


@Composable
private fun getCurrentRoute(currentRouteString: String): Route? {
    return when (currentRouteString) {
        Route.Home::class.qualifiedName -> Route.Home
        Route.OpenLibrary::class.qualifiedName -> Route.OpenLibraryGraph
        Route.AudioBook::class.qualifiedName -> Route.AudioBookGraph
        Route.Setting::class.qualifiedName -> Route.Setting
        Route.Summarize::class.qualifiedName -> Route.Summarize
        Route.OpenLibraryDetail()::class.qualifiedName -> Route.OpenLibraryDetail()
        Route.BookSavedScreen::class.qualifiedName -> Route.BookSavedScreen
        Route.AudioBookDetail()::class.qualifiedName -> Route.AudioBookDetail()
        else -> null
    }
}
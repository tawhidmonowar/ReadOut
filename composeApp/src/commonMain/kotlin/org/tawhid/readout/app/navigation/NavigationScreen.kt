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
import androidx.compose.material3.Scaffold
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
import org.tawhid.readout.app.navigation.components.settingNavigationItems
import org.tawhid.readout.core.setting.SettingViewModel
import org.tawhid.readout.core.theme.expandedNavigationBarWidth
import org.tawhid.readout.core.theme.mediumNavigationBarWidth
import org.tawhid.readout.core.utils.WindowSize
import org.tawhid.readout.core.utils.calculateWindowSize


@Composable
fun NavigationScreenRoot(
    settingViewModel: SettingViewModel = koinViewModel()
) {

    val windowSize = calculateWindowSize()
    val isExpandedScreen by remember(windowSize) { derivedStateOf { windowSize == WindowSize.Expanded } }
    val isMediumScreen by remember(windowSize) { derivedStateOf { windowSize == WindowSize.Medium } }
    val isCompactScreen by remember(windowSize) { derivedStateOf { windowSize == WindowSize.Compact } }

    val navigationItems = if (isExpandedScreen || isMediumScreen) {
        navigationItemsLists + settingNavigationItems
    } else { navigationItemsLists }

    val rootNavController = rememberNavController()
    val rootNavBackStackEntry by rootNavController.currentBackStackEntryAsState()
    val currentRoutePath by remember(rootNavBackStackEntry) {
        derivedStateOf {
            rootNavBackStackEntry?.destination?.route?.substringBefore("?")
                ?: Route.Home::class.qualifiedName.orEmpty()
        }
    }

    val currentRoute = getCurrentRoute(currentRoutePath)
    val navigationBarsVisibleRoutes = remember {
        mutableListOf(
            Route.Home,
            Route.OpenLibrary,
            Route.AudioBooks,
        ).apply {
            if (!isCompactScreen) {
                add(Route.Setting)
            }
        }
    }

    val isNavigationBarsVisible by remember(currentRoute) {
        derivedStateOf { currentRoute in navigationBarsVisibleRoutes }
    }

    NavigationScreen(
        settingViewModel = settingViewModel,
        rootNavController = rootNavController,
        currentRoute = currentRoute,
        navigationItems = navigationItems,
        isNavigationBarsVisible = isNavigationBarsVisible,
        isCompactScreen = isCompactScreen,
        isMediumScreen = isMediumScreen,
        isExpandedScreen = isExpandedScreen
    )
}

@Composable
private fun NavigationScreen(
    settingViewModel: SettingViewModel,
    modifier: Modifier = Modifier,
    rootNavController: NavHostController,
    currentRoute: Route?,
    navigationItems: List<NavigationItem>,
    isNavigationBarsVisible: Boolean,
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
                    settingViewModel = settingViewModel,
                    rootNavController = rootNavController,
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
                visible = isNavigationBarsVisible,
                enter = fadeIn() + slideInVertically(initialOffsetY = { fullHeight -> fullHeight }),
                exit = fadeOut() + slideOutVertically(targetOffsetY = { fullHeight -> fullHeight }),
                modifier = Modifier.align(Alignment.BottomCenter).padding(innerPadding)
            ) {

            }
        }
    }
}

@Composable
private fun getCurrentRoute(currentRouteString: String): Route? {
    return when (currentRouteString) {
        Route.Home::class.qualifiedName -> Route.Home
        Route.OpenLibrary::class.qualifiedName -> Route.OpenLibrary
        Route.AudioBooks::class.qualifiedName -> Route.AudioBooks
        Route.Setting::class.qualifiedName -> Route.Setting
        else -> null
    }
}
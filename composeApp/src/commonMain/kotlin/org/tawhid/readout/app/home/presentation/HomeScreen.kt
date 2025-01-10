package org.tawhid.readout.app.home.presentation

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.tawhid.readout.app.home.presentation.components.RecentlyViewedBookHorizontalGridList
import org.tawhid.readout.core.theme.Shapes
import org.tawhid.readout.core.theme.compactFeedWidth
import org.tawhid.readout.core.theme.compactScreenPadding
import org.tawhid.readout.core.theme.expandedFeedWidth
import org.tawhid.readout.core.theme.expandedScreenPadding
import org.tawhid.readout.core.theme.mediumFeedWidth
import org.tawhid.readout.core.theme.mediumScreenPadding
import org.tawhid.readout.core.theme.small
import org.tawhid.readout.core.theme.thin
import org.tawhid.readout.core.theme.zero
import org.tawhid.readout.core.ui.components.FeedTitleWithButton
import org.tawhid.readout.core.ui.components.FullScreenDialog
import org.tawhid.readout.core.ui.feed.Feed
import org.tawhid.readout.core.ui.feed.row
import org.tawhid.readout.core.ui.feed.title
import org.tawhid.readout.core.utils.WindowSizes
import readout.composeapp.generated.resources.Res
import readout.composeapp.generated.resources.home
import readout.composeapp.generated.resources.ic_auto_awesome_filled
import readout.composeapp.generated.resources.info
import readout.composeapp.generated.resources.setting
import readout.composeapp.generated.resources.summarize

@Composable
fun HomeScreenRoot(
    viewModel: HomeViewModel = koinViewModel(),
    onSettingClick: () -> Unit,
    onSummarizeClick: () -> Unit,
    innerPadding: PaddingValues,
    windowSize: WindowSizes
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    HomeScreen(
        state = state,
        innerPadding = innerPadding,
        windowSize = windowSize,
        onAction = { action ->
            when (action) {
                is HomeAction.OnSettingClick -> onSettingClick()
                is HomeAction.OnSummarizeClick -> onSummarizeClick()
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )

    if (state.showAboutDialog) {
        FullScreenDialog(
            onDismissRequest = { viewModel.onAction(HomeAction.OnHideAboutInfoDialog) },
            title = "Full-Screen Dialog",
            modifier = Modifier.padding(16.dp).clip(Shapes.medium),
            actions = {
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(onClick = { viewModel.onAction(HomeAction.OnHideAboutInfoDialog) }) {
                        Text("Close")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = { /* Handle confirm */ }) {
                        Text("Confirm")
                    }
                }
            },
            content = {
                Text(text = "This is a full-screen dialog in Jetpack Compose.")
            }
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreen(
    state: HomeState,
    onAction: (HomeAction) -> Unit,
    innerPadding: PaddingValues,
    windowSize: WindowSizes
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val startPadding by animateDpAsState(
        targetValue = innerPadding.calculateStartPadding(
            LayoutDirection.Ltr
        )
    )
    val topPadding by animateDpAsState(targetValue = innerPadding.calculateTopPadding())
    val endPadding by animateDpAsState(
        targetValue = innerPadding.calculateEndPadding(
            LayoutDirection.Ltr
        )
    )
    val bottomPadding by animateDpAsState(targetValue = innerPadding.calculateBottomPadding())
    val animatedPadding = PaddingValues(
        start = startPadding,
        top = topPadding,
        end = endPadding,
        bottom = bottomPadding
    )

    val gridState = rememberLazyGridState()

    Surface(modifier = Modifier.padding(animatedPadding)) {
        Scaffold(
            modifier = Modifier.fillMaxSize().nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(Res.string.home),
                            style = MaterialTheme.typography.titleLarge
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            onAction(HomeAction.OnShowAboutInfoDialog)
                        }) {
                            Icon(
                                imageVector = Icons.Outlined.Info,
                                contentDescription = stringResource(Res.string.info),
                            )
                        }
                    },
                    actions = {
                        if (windowSize.isCompactScreen) {
                            IconButton(onClick = {
                                onAction(HomeAction.OnSummarizeClick)
                            }) {
                                Icon(
                                    painter = painterResource(Res.drawable.ic_auto_awesome_filled),
                                    contentDescription = stringResource(Res.string.summarize),
                                )
                            }
                            IconButton(onClick = {
                                onAction(HomeAction.OnSettingClick)
                            }) {
                                Icon(
                                    imageVector = Icons.Outlined.Settings,
                                    contentDescription = stringResource(Res.string.setting),
                                )
                            }
                        }
                    },
                    scrollBehavior = scrollBehavior
                )
            }
        ) { innerPadding ->

            val columns = when {
                windowSize.isExpandedScreen -> GridCells.Adaptive(expandedFeedWidth)
                windowSize.isMediumScreen -> GridCells.Adaptive(mediumFeedWidth)
                else -> GridCells.Adaptive(compactFeedWidth)
            }

            val contentPadding = PaddingValues(horizontal = thin, vertical = thin)
            val verticalArrangement =
                if (!windowSize.isCompactScreen) Arrangement.spacedBy(small) else Arrangement.spacedBy(
                    zero
                )
            val horizontalArrangement =
                if (!windowSize.isCompactScreen) Arrangement.spacedBy(small) else Arrangement.spacedBy(
                    zero
                )

            val animatedStartPadding by animateDpAsState(
                targetValue = if (windowSize.isExpandedScreen) expandedScreenPadding
                else if (windowSize.isMediumScreen) mediumScreenPadding
                else compactScreenPadding,
                animationSpec = tween(durationMillis = 300)
            )

            val animatedEndPadding by animateDpAsState(
                targetValue = if (windowSize.isExpandedScreen) expandedScreenPadding
                else if (windowSize.isMediumScreen) mediumScreenPadding
                else compactScreenPadding,
                animationSpec = tween(durationMillis = 300)
            )

            Feed(
                modifier = Modifier.padding(
                    start = animatedStartPadding,
                    top = innerPadding.calculateTopPadding(),
                    end = animatedEndPadding,
                    bottom = innerPadding.calculateBottomPadding()
                ).fillMaxSize(),
                columns = columns,
                state = gridState,
                contentPadding = contentPadding,
                verticalArrangement = verticalArrangement,
                horizontalArrangement = horizontalArrangement
            ) {

                if (state.recentlyViewedBooks.isNotEmpty()) {
                    title(contentType = "recently-played-title") {
                        FeedTitleWithButton(
                            title = "Recently Viewed",
                            btnText = "View All",
                            onClick = {

                            }
                        )
                    }
                    row(contentType = "verified-shimmer-effect") {
                        RecentlyViewedBookHorizontalGridList(
                            books = state.recentlyViewedBooks,
                            onBookClick = {

                            }
                        )
                    }
                }



            }
        }
    }
}
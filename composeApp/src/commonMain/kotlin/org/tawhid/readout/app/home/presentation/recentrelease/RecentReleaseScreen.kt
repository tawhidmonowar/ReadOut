package org.tawhid.readout.app.home.presentation.recentrelease

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.LayoutDirection
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.tawhid.readout.book.audiobook.domain.entity.AudioBook
import org.tawhid.readout.book.audiobook.presentation.audiobook_home.components.AudioBookGridItem
import org.tawhid.readout.core.theme.compactFeedWidth
import org.tawhid.readout.core.theme.compactScreenPadding
import org.tawhid.readout.core.theme.expandedFeedWidth
import org.tawhid.readout.core.theme.expandedScreenPadding
import org.tawhid.readout.core.theme.mediumFeedWidth
import org.tawhid.readout.core.theme.mediumScreenPadding
import org.tawhid.readout.core.theme.small
import org.tawhid.readout.core.theme.thin
import org.tawhid.readout.core.theme.zero
import org.tawhid.readout.core.ui.feed.Feed
import org.tawhid.readout.core.utils.WindowSizes
import readout.composeapp.generated.resources.Res
import readout.composeapp.generated.resources.go_back
import readout.composeapp.generated.resources.no_saved_books_found
import readout.composeapp.generated.resources.saved_books

@Composable
fun RecentReleaseAudioBooksScreenRoot(
    viewModel: RecentReleaseViewModel = koinViewModel(),
    onBookClick: (AudioBook) -> Unit,
    onBackClick: () -> Unit,
    innerPadding: PaddingValues,
    windowSize: WindowSizes
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    RecentReleaseAudioBooksScreen(
        state = state,
        innerPadding = innerPadding,
        windowSize = windowSize,
        onAction = { action ->
            when (action) {
                is RecentReleaseAction.OnBookClick -> onBookClick(action.book)
                is RecentReleaseAction.OnBackClick -> onBackClick()
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RecentReleaseAudioBooksScreen(
    state: RecentReleaseState,
    innerPadding: PaddingValues,
    windowSize: WindowSizes,
    onAction: (RecentReleaseAction) -> Unit
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
    val bookSize = state.savedBooks.size

    Surface(modifier = Modifier.padding(animatedPadding)) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(Res.string.saved_books),
                            style = MaterialTheme.typography.titleLarge
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            onAction(RecentReleaseAction.OnBackClick)
                        }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                                contentDescription = stringResource(Res.string.go_back),
                            )
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

                if (state.savedBooks.isNotEmpty()) {
                    items(
                        count = bookSize,
                        key = { index -> state.savedBooks[index].id }
                    ) { index ->
                        val book = state.savedBooks[index]
                        AudioBookGridItem(
                            book = book,
                            onClick = {
                                onAction(RecentReleaseAction.OnBookClick(book))
                            }
                        )
                    }
                } else {
                    item {
                        Box(
                            modifier = Modifier.fillMaxSize().animateContentSize(),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(text = stringResource(Res.string.no_saved_books_found))
                        }
                    }
                }
            }
        }
    }
}
package org.tawhid.readout.book.audiobook.presentation.audiobook_home

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.tawhid.readout.book.audiobook.domain.AudioBook
import org.tawhid.readout.book.audiobook.presentation.audiobook_home.components.AudioBookGridItem
import org.tawhid.readout.book.audiobook.presentation.audiobook_home.components.AudioBookSearchResult
import org.tawhid.readout.book.openbook.presentation.openbook_home.BookHomeAction
import org.tawhid.readout.book.openbook.presentation.openbook_home.components.BookGridItem
import org.tawhid.readout.core.theme.compactFeedWidth
import org.tawhid.readout.core.theme.compactScreenPadding
import org.tawhid.readout.core.theme.expandedFeedWidth
import org.tawhid.readout.core.theme.expandedScreenPadding
import org.tawhid.readout.core.theme.mediumFeedWidth
import org.tawhid.readout.core.theme.mediumScreenPadding
import org.tawhid.readout.core.theme.small
import org.tawhid.readout.core.theme.thin
import org.tawhid.readout.core.theme.zero
import org.tawhid.readout.core.ui.components.ChipSectionScrollableLazyRow
import org.tawhid.readout.core.ui.components.EmbeddedSearchBar
import org.tawhid.readout.core.ui.components.FeedTitle
import org.tawhid.readout.core.ui.components.FeedTitleWithDropdown
import org.tawhid.readout.core.ui.feed.Feed
import org.tawhid.readout.core.ui.feed.action
import org.tawhid.readout.core.ui.feed.row
import org.tawhid.readout.core.ui.feed.title
import org.tawhid.readout.core.utils.WindowSizes
import org.tawhid.readout.core.utils.librivox_book_subject
import org.tawhid.readout.core.utils.openLibrary_book_subject
import readout.composeapp.generated.resources.Res
import readout.composeapp.generated.resources.audiobooks
import readout.composeapp.generated.resources.info
import readout.composeapp.generated.resources.search
import readout.composeapp.generated.resources.setting

@Composable
fun AudioBookHomeScreenRoot(
    viewModel: AudioBookHomeViewModel = koinViewModel(),
    onAudioBookClick: (AudioBook) -> Unit,
    onSettingClick: () -> Unit,
    innerPadding: PaddingValues,
    windowSize: WindowSizes
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    AudioBookHomeScreen(
        state = state,
        innerPadding = innerPadding,
        windowSize = windowSize,
        onAction = { action ->
            when (action) {
                is AudioBookHomeAction.OnAudioBookClick -> onAudioBookClick(action.audioBook)
                is AudioBookHomeAction.OnSettingClick -> onSettingClick()
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AudioBookHomeScreen(
    state: AudioBookHomeState,
    innerPadding: PaddingValues,
    windowSize: WindowSizes,
    onAction: (AudioBookHomeAction) -> Unit
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
    val keyboardController = LocalSoftwareKeyboardController.current
    val bookSize by mutableStateOf(state.searchResult.size)


    Surface(modifier = Modifier.padding(animatedPadding)) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(Res.string.audiobooks),
                            style = MaterialTheme.typography.titleLarge
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = {

                        }) {
                            Icon(
                                imageVector = Icons.Outlined.Info,
                                contentDescription = stringResource(Res.string.info),
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = {
                            onAction(AudioBookHomeAction.ActivateSearchMode)
                        }) {
                            Icon(
                                imageVector = Icons.Outlined.Search,
                                contentDescription = stringResource(Res.string.search),
                            )
                        }
                        if (windowSize.isCompactScreen) {
                            IconButton(onClick = {
                                onAction(AudioBookHomeAction.OnSettingClick)
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
                if (state.isSearchActive) {
                    EmbeddedSearchBar(
                        query = state.searchQuery,
                        onQueryChange = { query ->
                            onAction(AudioBookHomeAction.OnSearchQueryChange(query))
                        },
                        onSearch = {
                            keyboardController?.hide()
                        },
                        content = {
                            AudioBookSearchResult(
                                state = state,
                                onBookClick = { book ->
                                    keyboardController?.hide()
                                    onAction(AudioBookHomeAction.OnAudioBookClick(book))
                                }
                            )
                        },
                        onBack = {
                            onAction(AudioBookHomeAction.DeactivateSearchMode)
                        },
                        isActive = true
                    )
                }
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

            val selectedSubject = rememberSaveable { mutableStateOf<String?>(null) }

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

                row (
                    contentType = "subject-selector") {
                    ChipSectionScrollableLazyRow(
                        items = librivox_book_subject,
                        selectedItem = null,
                        onItemSelected = { selectedSubject.value = it },
                        itemLabel = { it }
                    )
                }

                title(contentType = "browse-title") {
                    FeedTitle(title = "Browse")
                }

                items(
                    count = bookSize,
                    key = { index -> state.searchResult[index].id }
                ) { index ->
                    val book = state.searchResult[index]

                    if (index == bookSize - 1 && !state.isEndReached) {
                        // onAction(BookHomeAction.)
                    }

                    AudioBookGridItem(
                        book = book,
                        onClick = {

                        }
                    )
                }
            }
        }
    }
}
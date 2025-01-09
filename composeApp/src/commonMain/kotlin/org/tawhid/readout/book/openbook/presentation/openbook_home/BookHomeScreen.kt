package org.tawhid.readout.book.openbook.presentation.openbook_home

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
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.LayoutDirection
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.tawhid.readout.book.openbook.domain.Book
import org.tawhid.readout.book.openbook.presentation.openbook_home.components.BookGridItem
import org.tawhid.readout.book.openbook.presentation.openbook_home.components.BookHorizontalGridList
import org.tawhid.readout.book.openbook.presentation.openbook_home.components.BookSearchResult
import org.tawhid.readout.core.theme.compactFeedWidth
import org.tawhid.readout.core.theme.compactScreenPadding
import org.tawhid.readout.core.theme.expandedFeedWidth
import org.tawhid.readout.core.theme.expandedScreenPadding
import org.tawhid.readout.core.theme.mediumFeedWidth
import org.tawhid.readout.core.theme.mediumScreenPadding
import org.tawhid.readout.core.theme.small
import org.tawhid.readout.core.theme.thin
import org.tawhid.readout.core.theme.zero
import org.tawhid.readout.core.ui.components.EmbeddedSearchBar
import org.tawhid.readout.core.ui.components.FeedTitleWithButton
import org.tawhid.readout.core.ui.components.FeedTitleWithDropdown
import org.tawhid.readout.core.ui.components.ShimmerEffect
import org.tawhid.readout.core.ui.components.ShimmerEffect.BookGridItemShimmer
import org.tawhid.readout.core.ui.feed.Feed
import org.tawhid.readout.core.ui.feed.row
import org.tawhid.readout.core.ui.feed.single
import org.tawhid.readout.core.ui.feed.title
import org.tawhid.readout.core.utils.WindowSizes
import org.tawhid.readout.core.utils.openLibrary_book_subject
import readout.composeapp.generated.resources.Res
import readout.composeapp.generated.resources.info
import readout.composeapp.generated.resources.open_library
import readout.composeapp.generated.resources.search
import readout.composeapp.generated.resources.setting
import readout.composeapp.generated.resources.view_more

@Composable
fun BookHomeScreenRoot(
    viewModel: BookHomeViewModel = koinViewModel(),
    onBookClick: (Book) -> Unit,
    onSettingClick: () -> Unit,
    innerPadding: PaddingValues,
    windowSize: WindowSizes
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    BookHomeScreen(
        state = state,
        innerPadding = innerPadding,
        windowSize = windowSize,
        onAction = { action ->
            when (action) {
                is BookHomeAction.OnBookClick -> onBookClick(action.book)
                is BookHomeAction.OnSettingClick -> onSettingClick()
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BookHomeScreen(
    state: BookHomeState,
    innerPadding: PaddingValues,
    windowSize: WindowSizes,
    onAction: (BookHomeAction) -> Unit
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
    val bookSize by mutableStateOf(state.browseBooks.size)
    val keyboardController = LocalSoftwareKeyboardController.current

    Surface(modifier = Modifier.padding(animatedPadding)) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(Res.string.open_library),
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
                            onAction(BookHomeAction.ActivateSearchMode)
                        }) {
                            Icon(
                                imageVector = Icons.Outlined.Search,
                                contentDescription = stringResource(Res.string.search),
                            )
                        }
                        if (windowSize.isCompactScreen) {
                            IconButton(onClick = {
                                onAction(BookHomeAction.OnSettingClick)
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
                            onAction(BookHomeAction.OnSearchQueryChange(query))
                        },
                        onSearch = {
                            keyboardController?.hide()
                        },
                        content = {
                            BookSearchResult(
                                state = state,
                                onBookClick = { book ->
                                    keyboardController?.hide()
                                    onAction(BookHomeAction.OnBookClick(book))
                                }
                            )
                        },
                        onBack = {
                            onAction(BookHomeAction.DeactivateSearchMode)
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
                title(contentType = "trending-title") {
                    FeedTitleWithButton(
                        title = "Trending",
                        btnText = stringResource(Res.string.view_more),
                        onClick = {

                        }
                    )
                }

                row(contentType = "verified-shimmer-effect") {

                    if (state.isTrendingLoading) {
                        ShimmerEffect.BookHorizontalGridItemShimmerEffect()
                    } else {
                        BookHorizontalGridList(
                            books = state.savedBooks,
                            onAction = { onAction((it)) }
                        )
                    }
                }

                title(contentType = "browse-title") {
                    FeedTitleWithDropdown(
                        title = "Browse",
                        dropDownList = openLibrary_book_subject,
                        onItemSelected = { selectedItem ->
                            onAction(BookHomeAction.OnSubjectChange(selectedItem.lowercase()))
                        }
                    )
                }

                items(
                    count = bookSize,
                    key = { index -> state.browseBooks[index].id }
                ) { index ->
                    val book = state.browseBooks[index]

                    if (index == bookSize - 1 && !state.endReached) {
                       // onAction(BookHomeAction.)
                    }

                    BookGridItem(
                        book = book,
                        onClick = {
                            onAction(BookHomeAction.OnBookClick(book))
                        }
                    )
                }
            }
        }
    }
}


//https://openlibrary.org/subjects/english.json?limit=50&offset=1
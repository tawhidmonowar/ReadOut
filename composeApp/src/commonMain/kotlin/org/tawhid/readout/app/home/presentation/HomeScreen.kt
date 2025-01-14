package org.tawhid.readout.app.home.presentation

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.tawhid.readout.book.audiobook.domain.entity.AudioBook
import org.tawhid.readout.book.audiobook.presentation.audiobook_home.components.AudioBookHorizontalGridList
import org.tawhid.readout.book.openbook.domain.Book
import org.tawhid.readout.book.openbook.presentation.openbook_home.components.BookGridItem
import org.tawhid.readout.core.theme.Shapes
import org.tawhid.readout.core.theme.compactFeedWidth
import org.tawhid.readout.core.theme.compactScreenPadding
import org.tawhid.readout.core.theme.expandedFeedWidth
import org.tawhid.readout.core.theme.expandedScreenPadding
import org.tawhid.readout.core.theme.large
import org.tawhid.readout.core.theme.medium
import org.tawhid.readout.core.theme.mediumFeedWidth
import org.tawhid.readout.core.theme.mediumScreenPadding
import org.tawhid.readout.core.theme.small
import org.tawhid.readout.core.theme.thin
import org.tawhid.readout.core.theme.zero
import org.tawhid.readout.core.ui.components.ErrorView
import org.tawhid.readout.core.ui.components.FullScreenDialog
import org.tawhid.readout.core.ui.feed.Feed
import org.tawhid.readout.core.ui.feed.FeedTitle
import org.tawhid.readout.core.ui.feed.FeedTitleWithButton
import org.tawhid.readout.core.ui.feed.row
import org.tawhid.readout.core.ui.feed.title
import org.tawhid.readout.core.utils.WindowSizes
import readout.composeapp.generated.resources.Res
import readout.composeapp.generated.resources.about_app
import readout.composeapp.generated.resources.app_icon
import readout.composeapp.generated.resources.app_name
import readout.composeapp.generated.resources.contact_email
import readout.composeapp.generated.resources.contact_label
import readout.composeapp.generated.resources.developer
import readout.composeapp.generated.resources.github_label
import readout.composeapp.generated.resources.github_url
import readout.composeapp.generated.resources.home
import readout.composeapp.generated.resources.ic_auto_awesome_filled
import readout.composeapp.generated.resources.info
import readout.composeapp.generated.resources.last_updated_date
import readout.composeapp.generated.resources.last_updated_label
import readout.composeapp.generated.resources.license_label
import readout.composeapp.generated.resources.license_type
import readout.composeapp.generated.resources.recently_released
import readout.composeapp.generated.resources.setting
import readout.composeapp.generated.resources.summarize
import readout.composeapp.generated.resources.trending_books
import readout.composeapp.generated.resources.version_label
import readout.composeapp.generated.resources.version_number
import readout.composeapp.generated.resources.view_all

@Composable
fun HomeScreenRoot(
    viewModel: HomeViewModel = koinViewModel(),
    onSettingClick: () -> Unit,
    onSummarizeClick: () -> Unit,
    onAudioBookClick: (AudioBook) -> Unit,
    onBookClick: (Book) -> Unit,
    onViewAllClick: () -> Unit,
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
                is HomeAction.OnViewAllNewReleasesClick -> onViewAllClick()
                is HomeAction.OnAudioBookClick -> onAudioBookClick(action.audioBook)
                is HomeAction.OnBookClick -> onBookClick(action.book)
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )

    if (state.showAboutDialog) {
        ShowAboutDialog(
            onDismiss = {
                viewModel.onAction(HomeAction.OnHideAboutDialog)
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
                            onAction(HomeAction.OnShowAboutDialog)
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

                title(contentType = "audiobook-title") {
                    FeedTitleWithButton(
                        title = stringResource(Res.string.recently_released),
                        btnText = stringResource(Res.string.view_all),
                        onClick = {
                            onAction(HomeAction.OnViewAllNewReleasesClick)
                        },
                        modifier = Modifier.padding(vertical = small)
                    )
                }

                if (state.audioBooksErrorMsg != null) {
                    row(contentType = "error") {
                        ErrorView(
                            errorMsg = state.audioBooksErrorMsg,
                            onRetryClick = {
                                onAction(HomeAction.OnLoadAudioBooks)
                            }
                        )
                    }
                } else if (state.isAudioBooksLoading) {
                    row(contentType = "loading") {
                        Box(
                            modifier = Modifier.fillMaxSize().animateContentSize(),
                            contentAlignment = Alignment.Center,
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.padding(large)
                            )
                        }
                    }
                } else {
                    row(contentType = "recently-released-audiobooks") {
                        AudioBookHorizontalGridList(
                            books = state.recentlyReleasedAudioBooks,
                            onBookClick = { audioBook ->
                                onAction(HomeAction.OnAudioBookClick(audioBook))
                            }
                        )
                    }
                }

                title(contentType = "trending-books-title") {
                    FeedTitle(
                        title = stringResource(Res.string.trending_books),
                        modifier = Modifier.padding(vertical = small)
                    )
                }

                if (state.trendingErrorMsg != null) {
                    row(contentType = "error") {
                        ErrorView(
                            errorMsg = state.trendingErrorMsg,
                            onRetryClick = {
                                onAction(HomeAction.OnLoadTrendingBooks)
                            }
                        )
                    }
                } else if (!state.isAudioBooksLoading && state.isTrendingLoading) {
                    row(contentType = "loading") {
                        Box(
                            modifier = Modifier.fillMaxSize().animateContentSize(),
                            contentAlignment = Alignment.Center,
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.padding(large)
                            )
                        }
                    }
                } else {
                    items(
                        count = state.trendingBooks.size,
                        key = { index -> state.trendingBooks[index].id }
                    ) { index ->
                        val book = state.trendingBooks[index]
                        BookGridItem(
                            book = book,
                            onClick = {
                                onAction(HomeAction.OnBookClick(book))
                            }
                        )
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(large))
                }
            }
        }
    }
}

@Composable
private fun ShowAboutDialog(
    onDismiss: () -> Unit,
) {
    FullScreenDialog(
        onDismissRequest = { onDismiss() },
        title = "About",
        modifier = Modifier.padding(16.dp).clip(Shapes.medium),
        actions = {
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(onClick = { onDismiss() }) {
                    Text("Close")
                }
            }
        },
        content = {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
                Image(
                    modifier = Modifier.size(100.dp).padding(bottom = medium),
                    painter = painterResource(Res.drawable.app_icon),
                    contentDescription = "App Icon"
                )
                Text(
                    style = MaterialTheme.typography.titleLarge,
                    text = stringResource(Res.string.app_name)
                )
                Text(
                    modifier = Modifier.padding(bottom = small),
                    style = MaterialTheme.typography.bodyMedium,
                    text = stringResource(Res.string.developer)
                )
                Spacer(
                    modifier = Modifier.height(2.dp).fillMaxWidth(0.7f)
                        .background(MaterialTheme.colorScheme.primary)
                )
                Text(
                    modifier = Modifier.padding(vertical = medium),
                    style = MaterialTheme.typography.bodyMedium,
                    text = stringResource(Res.string.about_app)
                )

                Text(
                    style = MaterialTheme.typography.labelLarge,
                    text = stringResource(Res.string.last_updated_label)
                )
                Text(
                    style = MaterialTheme.typography.bodyMedium,
                    text = stringResource(Res.string.last_updated_date)
                )

                Text(
                    modifier = Modifier.padding(top = medium),
                    style = MaterialTheme.typography.labelLarge,
                    text = stringResource(Res.string.contact_label)
                )
                Text(
                    style = MaterialTheme.typography.bodyMedium,
                    text = stringResource(Res.string.contact_email)
                )

                Text(
                    modifier = Modifier.padding(top = medium),
                    style = MaterialTheme.typography.labelLarge,
                    text = stringResource(Res.string.github_label)
                )
                Text(
                    style = MaterialTheme.typography.bodyMedium,
                    text = stringResource(Res.string.github_url)
                )

                Text(
                    modifier = Modifier.padding(top = medium),
                    style = MaterialTheme.typography.labelLarge,
                    text = stringResource(Res.string.license_label)
                )
                Text(
                    style = MaterialTheme.typography.bodyMedium,
                    text = stringResource(Res.string.license_type)
                )

                Text(
                    modifier = Modifier.padding(top = medium),
                    style = MaterialTheme.typography.labelLarge,
                    text = stringResource(Res.string.version_label)
                )
                Text(
                    style = MaterialTheme.typography.bodyMedium,
                    text = stringResource(Res.string.version_number)
                )
            }
        }
    )
}
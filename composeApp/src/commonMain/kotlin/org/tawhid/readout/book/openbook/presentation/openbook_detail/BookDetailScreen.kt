package org.tawhid.readout.book.openbook.presentation.openbook_detail

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.tawhid.readout.book.openbook.domain.entity.Book
import org.tawhid.readout.core.theme.Shapes
import org.tawhid.readout.core.theme.compactScreenPadding
import org.tawhid.readout.core.theme.expandedScreenPadding
import org.tawhid.readout.core.theme.large
import org.tawhid.readout.core.theme.medium
import org.tawhid.readout.core.theme.mediumScreenPadding
import org.tawhid.readout.core.theme.small
import org.tawhid.readout.core.ui.components.BookCoverImage
import org.tawhid.readout.core.utils.OPEN_LIBRARY_BASE_URL
import org.tawhid.readout.core.utils.WindowSizes
import readout.composeapp.generated.resources.Res
import readout.composeapp.generated.resources.about_book
import readout.composeapp.generated.resources.book_details
import readout.composeapp.generated.resources.book_summary
import readout.composeapp.generated.resources.bookmark
import readout.composeapp.generated.resources.browse
import readout.composeapp.generated.resources.description_unavailable
import readout.composeapp.generated.resources.go_back
import readout.composeapp.generated.resources.ic_bookmark_filled
import readout.composeapp.generated.resources.ic_bookmark_outlined
import readout.composeapp.generated.resources.ic_browse
import readout.composeapp.generated.resources.ic_headphones
import readout.composeapp.generated.resources.ic_notes
import readout.composeapp.generated.resources.summary
import readout.composeapp.generated.resources.summary_generated_with_ai

@Composable
fun BookDetailScreenRoot(
    viewModel: BookDetailViewModel,
    onBackClick: () -> Unit,
    windowSize: WindowSizes,
    innerPadding: PaddingValues
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    BookDetailScreen(
        state = state,
        innerPadding = innerPadding,
        windowSize = windowSize,
        onAction = { action ->
            when (action) {
                is BookDetailAction.OnBackClick -> onBackClick()
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BookDetailScreen(
    state: BookDetailState,
    innerPadding: PaddingValues,
    windowSize: WindowSizes,
    onAction: (BookDetailAction) -> Unit
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

    val urlHandler = LocalUriHandler.current
    val scrollState = rememberScrollState()
    val scrollToBottom = remember { mutableStateOf(false) }

    LaunchedEffect(scrollToBottom.value) {
        if (scrollToBottom.value) {
            scrollState.animateScrollTo(scrollState.maxValue)
            scrollToBottom.value = false
        }
    }

    Surface(modifier = Modifier.padding(animatedPadding)) {
        Scaffold(
            modifier = Modifier.fillMaxSize().nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(Res.string.book_details),
                            style = MaterialTheme.typography.titleLarge
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            onAction(BookDetailAction.OnBackClick)
                        }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                                contentDescription = stringResource(Res.string.go_back),
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = {
                            urlHandler.openUri(OPEN_LIBRARY_BASE_URL + "/works/${state.book?.id}")
                        }) {
                            Icon(
                                painter = painterResource(Res.drawable.ic_browse),
                                contentDescription = stringResource(Res.string.browse),
                            )
                        }
                        IconButton(onClick = {
                            onAction(BookDetailAction.OnSaveClick)
                        }) {
                            Icon(
                                painter = if (state.isSaved) {
                                    painterResource(Res.drawable.ic_bookmark_filled)
                                } else {
                                    painterResource(Res.drawable.ic_bookmark_outlined)
                                },
                                contentDescription = stringResource(Res.string.bookmark),
                            )
                        }
                    },
                    scrollBehavior = scrollBehavior
                )
            }
        ) { innerPadding ->

            val animatedStartPadding by animateDpAsState(
                targetValue = if (windowSize.isExpandedScreen) expandedScreenPadding
                else if (windowSize.isMediumScreen) mediumScreenPadding
                else compactScreenPadding + small,
                animationSpec = tween(durationMillis = 300)
            )

            val animatedEndPadding by animateDpAsState(
                targetValue = if (windowSize.isExpandedScreen) expandedScreenPadding
                else if (windowSize.isMediumScreen) mediumScreenPadding
                else compactScreenPadding + small,
                animationSpec = tween(durationMillis = 300)
            )

            BoxWithConstraints(
                modifier = Modifier.fillMaxSize().verticalScroll(scrollState).padding(
                    start = animatedStartPadding,
                    top = innerPadding.calculateTopPadding(),
                    end = animatedEndPadding,
                    bottom = innerPadding.calculateBottomPadding()
                )
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    state.book?.let { book ->
                        if (windowSize.isCompactScreen) {
                            BookDetailCompactLayout(
                                book = book,
                                isSummaryAvailable = state.isSummaryAvailable,
                                onAction = onAction,
                                state = state
                            )
                        } else {
                            BookDetailExpandedLayout(
                                book = book,
                                isSummaryAvailable = state.isSummaryAvailable,
                                onAction = onAction,
                                state = state
                            )
                        }
                    }

                    Text(
                        text = stringResource(Res.string.about_book),
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.align(Alignment.Start).fillMaxWidth().padding(
                            top = large,
                            bottom = small
                        )
                    )

                    if (state.isLoading) {
                        Box(
                            modifier = Modifier.fillMaxSize().padding(medium),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    } else {
                        (if (state.book?.description.isNullOrBlank()) {
                            stringResource(Res.string.description_unavailable)
                        } else {
                            state.book?.description
                        })?.let {
                            Text(
                                text = it,
                                style = MaterialTheme.typography.bodyLarge,
                                textAlign = TextAlign.Justify,
                            )
                            Spacer(modifier = Modifier.height(medium))
                        }
                    }

                    Text(
                        text = stringResource(Res.string.book_summary),
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier
                            .align(Alignment.Start)
                            .fillMaxWidth()
                            .padding(top = large)
                    )

                    Text(
                        text = stringResource(Res.string.summary_generated_with_ai),
                        style = MaterialTheme.typography.bodyMedium
                    )

                    if (state.isSummaryLoading) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(medium),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    } else {
                        state.summary?.let {
                            Text(
                                text = it,
                                style = MaterialTheme.typography.bodyLarge,
                                textAlign = TextAlign.Justify,
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
private fun BookTitleAndAuthors(
    title: String,
    authors: List<String>
) {
    Text(
        text = title,
        style = MaterialTheme.typography.headlineSmall,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(bottom = small)
    )
    Text(
        text = "By ${authors.joinToString()}",
        style = MaterialTheme.typography.titleMedium
    )
}

@Composable
private fun BookDetailButton(
    state: BookDetailState,
    isSummaryAvailable: Boolean,
    onAction: (BookDetailAction) -> Unit
) {

    if (isSummaryAvailable) {

        Button(
            onClick = {
                onAction(BookDetailAction.OnSummaryClick)
            }) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_notes),
                    contentDescription = "Read Summary",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "Read Summary")
            }
        }
        Spacer(modifier = Modifier.width(small))
        Button(
            onClick = {
                onAction(BookDetailAction.OnSummaryPlayClick)
                state.summaryAudioByteArray?.let {

                }
            }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_headphones),
                    contentDescription = "Listen",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "Listen")
            }
        }

    } else {
        Button(
            onClick = {
                onAction(BookDetailAction.OnSummaryClick)
                // scrollToBottom.value = true
            },
            content = {
                Text(text = stringResource(Res.string.summary))
            }
        )
    }
}

@Composable
private fun BookDetailCompactLayout(
    state: BookDetailState,
    book: Book,
    isSummaryAvailable: Boolean,
    onAction: (BookDetailAction) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = small)
            .clip(Shapes.medium)
            .padding(large),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BookCoverImage(imgUrl = book.imgUrl)
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(large))
            BookTitleAndAuthors(title = book.title, authors = book.authors)
            Spacer(modifier = Modifier.height(medium))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                BookDetailButton(
                    isSummaryAvailable = isSummaryAvailable,
                    onAction = onAction,
                    state = state
                )
            }
        }
    }
}

@Composable
private fun BookDetailExpandedLayout(
    state: BookDetailState,
    book: Book,
    isSummaryAvailable: Boolean,
    onAction: (BookDetailAction) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = medium)
            .clip(Shapes.medium)
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .padding(large)
    ) {

        BookCoverImage(imgUrl = book.imgUrl)

        Column(
            modifier = Modifier.fillMaxWidth().padding(start = large),
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(medium))

            BookTitleAndAuthors(
                title = book.title,
                authors = book.authors
            )

            Spacer(modifier = Modifier.height(medium))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                BookDetailButton(
                    isSummaryAvailable = isSummaryAvailable,
                    onAction = onAction,
                    state = state
                )
            }
        }
    }
}
package org.tawhid.readout.book.openbook.presentation.openbook_detail

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.rememberAsyncImagePainter
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.tawhid.readout.core.theme.Shapes
import org.tawhid.readout.core.theme.compactScreenPadding
import org.tawhid.readout.core.theme.expandedScreenPadding
import org.tawhid.readout.core.theme.extraSmall
import org.tawhid.readout.core.theme.large
import org.tawhid.readout.core.theme.medium
import org.tawhid.readout.core.theme.mediumScreenPadding
import org.tawhid.readout.core.theme.small
import org.tawhid.readout.core.ui.animation.PulseAnimation
import org.tawhid.readout.core.utils.WindowSizes
import readout.composeapp.generated.resources.Res
import readout.composeapp.generated.resources.about_book
import readout.composeapp.generated.resources.book_cover
import readout.composeapp.generated.resources.book_cover_error_img
import readout.composeapp.generated.resources.book_details
import readout.composeapp.generated.resources.book_summary
import readout.composeapp.generated.resources.bookmark
import readout.composeapp.generated.resources.description_unavailable
import readout.composeapp.generated.resources.go_back
import readout.composeapp.generated.resources.ic_bookmark_outlined
import readout.composeapp.generated.resources.play
import readout.composeapp.generated.resources.share
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
    var imageLoadResult by remember { mutableStateOf<Result<Painter>?>(null) }
    val painter = rememberAsyncImagePainter(
        model = state.book?.imgUrl,
        onSuccess = {
            val size = it.painter.intrinsicSize
            imageLoadResult = if (size.width > 1 && size.height > 1) {
                Result.success(it.painter)
            } else {
                Result.failure(Exception("Invalid image dimensions"))
            }
        }
    )

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
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
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
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(
                        start = animatedStartPadding,
                        top = innerPadding.calculateTopPadding(),
                        end = animatedEndPadding,
                        bottom = innerPadding.calculateBottomPadding()
                    )
            ) {
                Column(modifier = Modifier.fillMaxSize()) {


                    if (windowSize.isCompactScreen) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = small)
                                .clip(Shapes.medium)
                                .padding(large),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                modifier = Modifier
                                    .height(250.dp)
                                    .aspectRatio(2 / 3f, matchHeightConstraintsFirst = true),
                                contentAlignment = Alignment.TopCenter
                            ) {
                                ElevatedCard(
                                    modifier = Modifier.fillMaxSize(),
                                    shape = Shapes.small,
                                    elevation = CardDefaults.elevatedCardElevation(defaultElevation = small)
                                ) {
                                    AnimatedContent(targetState = imageLoadResult) { result ->
                                        Box(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .background(Color.White)
                                                .padding(extraSmall)
                                                .clip(Shapes.extraSmall),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            when (result) {
                                                null -> PulseAnimation(modifier = Modifier.size(100.dp))
                                                else -> Image(
                                                    painter = if (result.isSuccess) painter else {
                                                        painterResource(Res.drawable.book_cover_error_img)
                                                    },
                                                    contentDescription = stringResource(Res.string.book_cover),
                                                    modifier = Modifier.fillMaxSize(),
                                                    contentScale = if (result.isSuccess) ContentScale.Crop else ContentScale.Fit
                                                )
                                            }
                                        }
                                    }
                                }
                            }

                            state.book?.let { book ->
                                Column(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Spacer(modifier = Modifier.height(large))
                                    Text(
                                        text = book.title,
                                        style = MaterialTheme.typography.headlineSmall,
                                        modifier = Modifier.padding(bottom = small)
                                    )
                                    Text(
                                        text = "By ${book.authors.joinToString()}",
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                    Spacer(modifier = Modifier.height(medium))
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.Center
                                    ) {

                                        if (state.isSummaryAvailable) {
                                            Button(
                                                onClick = {
                                                    scrollToBottom.value = true
                                                },
                                                content = {
                                                    Text(text = stringResource(Res.string.summary))
                                                }
                                            )

                                            Spacer(modifier = Modifier.width(extraSmall))

                                            Button(
                                                onClick = {
                                                    scrollToBottom.value = true
                                                },
                                                content = {
                                                    Text(text = stringResource(Res.string.play))
                                                }
                                            )
                                        } else {
                                            Button(
                                                onClick = {
                                                    scrollToBottom.value = true
                                                },
                                                content = {
                                                    Text(text = stringResource(Res.string.summary))
                                                }
                                            )
                                        }

                                        Spacer(modifier = Modifier.width(extraSmall))

                                        IconButton(
                                            onClick = { }
                                        ) {
                                            Icon(
                                                painter = painterResource(Res.drawable.ic_bookmark_outlined),
                                                contentDescription = stringResource(Res.string.bookmark),
                                            )
                                        }
                                        IconButton(
                                            onClick = { }
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Share,
                                                contentDescription = stringResource(Res.string.share),
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = medium)
                                .clip(Shapes.medium)
                                .background(MaterialTheme.colorScheme.surfaceContainer)
                                .padding(large)
                        ) {
                            Box(
                                modifier = Modifier
                                    .height(250.dp)
                                    .aspectRatio(2 / 3f, matchHeightConstraintsFirst = true),
                                contentAlignment = Alignment.TopEnd
                            ) {
                                ElevatedCard(
                                    modifier = Modifier.fillMaxSize(),
                                    shape = Shapes.small,
                                    elevation = CardDefaults.elevatedCardElevation(defaultElevation = small)
                                ) {
                                    AnimatedContent(targetState = imageLoadResult) { result ->
                                        Box(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .background(Color.White)
                                                .padding(extraSmall)
                                                .clip(Shapes.extraSmall),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            when (result) {
                                                null -> PulseAnimation(modifier = Modifier.size(100.dp))
                                                else -> Image(
                                                    painter = if (result.isSuccess) painter else {
                                                        painterResource(Res.drawable.book_cover_error_img)
                                                    },
                                                    contentDescription = stringResource(Res.string.book_cover),
                                                    modifier = Modifier.fillMaxSize(),
                                                    contentScale = if (result.isSuccess) ContentScale.Crop else ContentScale.Fit
                                                )
                                            }
                                        }
                                    }
                                }
                            }

                            state.book?.let { book ->
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = large),
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Spacer(modifier = Modifier.height(medium))
                                    Text(
                                        text = book.title,
                                        style = MaterialTheme.typography.headlineSmall,
                                        modifier = Modifier.padding(bottom = small)
                                    )
                                    Text(
                                        text = "By ${book.authors.joinToString()}",
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                    Spacer(modifier = Modifier.height(medium))
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.Start
                                    ) {

                                        if (state.isSummaryAvailable) {
                                            Button(
                                                onClick = {
                                                    scrollToBottom.value = true
                                                },
                                                content = {
                                                    Text(text = stringResource(Res.string.summary))
                                                }
                                            )

                                            Spacer(modifier = Modifier.width(extraSmall))

                                            Button(
                                                onClick = {
                                                    scrollToBottom.value = true
                                                },
                                                content = {
                                                    Text(text = stringResource(Res.string.play))
                                                }
                                            )
                                        } else {
                                            Button(
                                                onClick = {
                                                    scrollToBottom.value = true
                                                },
                                                content = {
                                                    Text(text = stringResource(Res.string.summary))
                                                }
                                            )
                                        }

                                        Spacer(modifier = Modifier.width(extraSmall))

                                        IconButton(
                                            onClick = { }
                                        ) {
                                            Icon(
                                                painter = painterResource(Res.drawable.ic_bookmark_outlined),
                                                contentDescription = stringResource(Res.string.bookmark),
                                            )
                                        }
                                        IconButton(
                                            onClick = { }
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Share,
                                                contentDescription = stringResource(Res.string.share),
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                    Text(
                        text = stringResource(Res.string.about_book),
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier
                            .align(Alignment.Start)
                            .fillMaxWidth()
                            .padding(
                                top = large,
                                bottom = small
                            )
                    )

                    if (state.isLoading) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(medium),
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

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(medium),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}
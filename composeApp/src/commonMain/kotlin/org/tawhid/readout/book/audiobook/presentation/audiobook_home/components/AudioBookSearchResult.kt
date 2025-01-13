package org.tawhid.readout.book.audiobook.presentation.audiobook_home.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import org.tawhid.readout.book.audiobook.domain.entity.AudioBook
import org.tawhid.readout.book.audiobook.presentation.audiobook_home.AudioBookHomeState

@Composable
fun AudioBookSearchResult(
    state: AudioBookHomeState,
    onBookClick: (AudioBook) -> Unit
) {
    val searchResultListState = rememberLazyListState()

    LaunchedEffect(state.searchResult) {
        searchResultListState.animateScrollToItem(0)
    }

    Surface(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                if (state.isSearchLoading) {
                    CircularProgressIndicator()
                } else {
                    when {
                        state.searchErrorMsg != null -> {
                            Text(
                                text = state.searchErrorMsg.asString(),
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.error
                            )
                        }

                        state.searchResult.isEmpty() -> {
                            Text(
                                text = "No search result",
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }

                        else -> {
                            AudioBookList(
                                books = state.searchResult,
                                onBookClick = { book ->
                                    onBookClick(book)
                                },
                                modifier = Modifier.fillMaxSize(),
                                scrollState = searchResultListState
                            )
                        }
                    }
                }
            }
        }
    }
}
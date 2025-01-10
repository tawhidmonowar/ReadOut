package org.tawhid.readout.app.home.presentation.components

import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.tawhid.readout.app.home.domain.RecentlyViewedBooks
import org.tawhid.readout.book.openbook.presentation.openbook_home.BookHomeAction
import org.tawhid.readout.book.openbook.presentation.openbook_home.components.BookGridItem
import org.tawhid.readout.core.theme.horizontalGridMaxHeight
import org.tawhid.readout.core.theme.horizontalGridMaxWidth
import org.tawhid.readout.core.theme.zero

@Composable
fun RecentlyViewedBookHorizontalGridList(
    books: List<RecentlyViewedBooks>,
    onBookClick: (RecentlyViewedBooks) -> Unit,
    modifier: Modifier = Modifier
) {
    val rowState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val bookSize by mutableStateOf(books.size)
    LaunchedEffect(books) { rowState.scrollToItem(0) }

    LazyRow(
        state = rowState,
        modifier = modifier
            .heightIn(max = horizontalGridMaxHeight)
            .pointerInput(Unit) {
                detectHorizontalDragGestures { change, dragAmount ->
                    change.consume()
                    coroutineScope.launch {
                        rowState.scrollBy(-dragAmount)
                    }
                }
            },
        horizontalArrangement = Arrangement.spacedBy(zero)
    ) {
        items(bookSize, key = { books[it].id }, contentType = { "book" }) {
            RecentlyViewedBookGridItem(
                book = books[it],
                onClick = {
                    onBookClick(books[it])
                },
                modifier = Modifier.width(150.dp)
            )
        }
    }
}
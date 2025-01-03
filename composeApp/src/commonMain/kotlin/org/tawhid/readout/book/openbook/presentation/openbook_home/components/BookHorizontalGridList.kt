package org.tawhid.readout.book.openbook.presentation.openbook_home.components

import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import kotlinx.coroutines.launch
import org.tawhid.readout.book.openbook.domain.Book
import org.tawhid.readout.book.openbook.presentation.openbook_home.BookHomeAction
import org.tawhid.readout.core.theme.horizontalGridMaxHeight
import org.tawhid.readout.core.theme.horizontalGridMaxWidth
import org.tawhid.readout.core.theme.zero

@Composable
fun BookHorizontalGridList(
    books: List<Book>,
    onAction: (BookHomeAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val gridState = rememberLazyGridState()
    val coroutineScope = rememberCoroutineScope()
    val bookSize by mutableStateOf(books.size)

    LazyHorizontalGrid(
        state = gridState,
        rows = GridCells.Fixed(2),
        modifier = modifier
            .heightIn(max = horizontalGridMaxHeight)
            .pointerInput(Unit) {
                detectHorizontalDragGestures { change, dragAmount ->
                    change.consume()
                    coroutineScope.launch {
                        gridState.scrollBy(-dragAmount)
                    }
                }
            },
        verticalArrangement = Arrangement.spacedBy(zero),
        horizontalArrangement = Arrangement.spacedBy(zero)
    ) {
        items(bookSize, key = { books[it].id }, contentType = { "book" }) {
            BookGridItem(
                book = books[it],
                onClick = {
                    onAction(BookHomeAction.OnBookClick(books[it]))
                },
                modifier = Modifier.width(horizontalGridMaxWidth)
            )
        }
    }
}
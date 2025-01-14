package org.tawhid.readout.book.openbook.presentation.openbook_home.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.tawhid.readout.book.openbook.domain.entity.Book
import org.tawhid.readout.core.theme.maxWidthIn
import org.tawhid.readout.core.theme.medium

@Composable
fun BookList(
    books: List<Book>,
    onBookClick: (Book) -> Unit,
    modifier: Modifier = Modifier,
    scrollState: LazyListState = rememberLazyListState()
) {
    LazyColumn(
        modifier = modifier,
        state = scrollState,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(
            items = books,
            key = { it.id }
        ) { book ->
            BookListItem(
                book = book,
                modifier = Modifier
                    .widthIn(max = maxWidthIn)
                    .fillMaxWidth()
                    .padding(horizontal = medium),
                onBookClick = {
                    onBookClick(book)
                }
            )
        }
    }
}
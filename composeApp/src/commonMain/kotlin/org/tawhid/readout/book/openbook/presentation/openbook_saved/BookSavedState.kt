package org.tawhid.readout.book.openbook.presentation.openbook_saved

import org.tawhid.readout.book.openbook.domain.entity.Book

data class BookSavedState(
    val savedBooks: List<Book> = emptyList()
)
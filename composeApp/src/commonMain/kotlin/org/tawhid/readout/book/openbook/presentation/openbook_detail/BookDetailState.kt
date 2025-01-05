package org.tawhid.readout.book.openbook.presentation.openbook_detail

import org.tawhid.readout.book.openbook.domain.Book

data class BookDetailState(
    val isLoading: Boolean = true,
    val isSaved: Boolean = false,
    val isSummaryAvailable: Boolean = true,
    val book: Book? = null,
    val summary: String? = null
)
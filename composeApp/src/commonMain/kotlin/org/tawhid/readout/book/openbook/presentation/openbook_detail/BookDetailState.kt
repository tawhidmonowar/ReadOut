package org.tawhid.readout.book.openbook.presentation.openbook_detail

import org.tawhid.readout.book.openbook.domain.Book
import org.tawhid.readout.core.utils.UiText

data class BookDetailState(
    val isLoading: Boolean = true,
    val isSaved: Boolean = false,
    val book: Book? = null,
    val isSummaryLoading: Boolean = false,
    val isSummaryAvailable: Boolean = true,
    val summaryErrorMsg: UiText? = null,
    val summary: String? = null
)
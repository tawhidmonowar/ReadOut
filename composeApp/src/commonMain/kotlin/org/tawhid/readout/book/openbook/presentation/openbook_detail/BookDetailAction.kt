package org.tawhid.readout.book.openbook.presentation.openbook_detail

import org.tawhid.readout.book.openbook.domain.Book

sealed interface BookDetailAction {
    data object OnBackClick : BookDetailAction
    data object OnSaveClick : BookDetailAction
    data object OnSummaryClick : BookDetailAction
    data class OnSelectedBookChange(val book: Book) : BookDetailAction
}
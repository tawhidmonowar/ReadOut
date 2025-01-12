package org.tawhid.readout.book.openbook.presentation.openbook_saved

import org.tawhid.readout.book.openbook.domain.Book

sealed interface BookSavedAction {
    data class OnBookClick(val book: Book) : BookSavedAction
    data object OnBackClick : BookSavedAction
}
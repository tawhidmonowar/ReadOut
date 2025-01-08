package org.tawhid.readout.book.audiobook.presentation.audiobook_detail

import org.tawhid.readout.book.audiobook.domain.AudioBook

sealed interface AudioBookDetailAction {
    data object OnBackClick : AudioBookDetailAction
    data object OnSaveClick : AudioBookDetailAction
    data object OnSummaryClick : AudioBookDetailAction
    data class OnTabSelected(val index: Int) : AudioBookDetailAction
    data class OnSelectedBookChange(val audioBook: AudioBook) : AudioBookDetailAction
}
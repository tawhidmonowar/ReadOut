package org.tawhid.readout.book.audiobook.presentation.audiobook_home

import org.tawhid.readout.book.audiobook.domain.AudioBook
import org.tawhid.readout.book.openbook.presentation.openbook_home.BookHomeAction

interface AudioBookHomeAction {
    data class OnSearchQueryChange(val query: String) : AudioBookHomeAction
    data class OnAudioBookClick(val audioBook: AudioBook) : AudioBookHomeAction
    data object OnSettingClick : AudioBookHomeAction
    data object ActivateSearchMode : AudioBookHomeAction
    data object DeactivateSearchMode : AudioBookHomeAction

}
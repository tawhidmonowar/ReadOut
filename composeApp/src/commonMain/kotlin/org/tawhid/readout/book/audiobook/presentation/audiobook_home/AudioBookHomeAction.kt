package org.tawhid.readout.book.audiobook.presentation.audiobook_home

import org.tawhid.readout.book.audiobook.domain.entity.AudioBook
import org.tawhid.readout.book.openbook.presentation.openbook_home.BookHomeAction

interface AudioBookHomeAction {
    data class OnSearchQueryChange(val query: String) : AudioBookHomeAction
    data class OnAudioBookClick(val audioBook: AudioBook) : AudioBookHomeAction
    data class OnGenreSelect(val genre: String) : AudioBookHomeAction
    data object OnGetBrowseAudioBooks : AudioBookHomeAction
    data object OnSettingClick : AudioBookHomeAction
    data object OnViewALlClick : AudioBookHomeAction
    data object ActivateSearchMode : AudioBookHomeAction
    data object DeactivateSearchMode : AudioBookHomeAction

    data object OnShowInfoDialog : AudioBookHomeAction
    data object OnHideInfoDialog : AudioBookHomeAction
}
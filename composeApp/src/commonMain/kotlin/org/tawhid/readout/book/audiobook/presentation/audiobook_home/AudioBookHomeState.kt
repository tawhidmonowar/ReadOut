package org.tawhid.readout.book.audiobook.presentation.audiobook_home

import org.tawhid.readout.book.audiobook.domain.AudioBook
import org.tawhid.readout.core.utils.UiText

data class AudioBookHomeState(
    val searchQuery: String = "",
    val isSearchLoading: Boolean = false,
    val isSearchActive: Boolean = false,
    val searchErrorMsg: UiText? = null,
    val searchResult: List<AudioBook> = emptyList(),

    val isBrowseLoading: Boolean = false,
    val isEndReached: Boolean = false,
    val browseErrorMsg: UiText? = null,
    val browseAudioBooks: List<AudioBook> = emptyList(),
)
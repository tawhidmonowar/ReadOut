package org.tawhid.readout.book.audiobook.presentation.audiobook_detail

import org.tawhid.readout.book.audiobook.domain.AudioBook
import org.tawhid.readout.book.audiobook.domain.AudioBookTracks
import org.tawhid.readout.core.utils.UiText

data class AudioBookDetailState(
    val isLoading: Boolean = true,
    val isSaved: Boolean = false,
    val audioBook: AudioBook? = null,
    val selectedTabIndex: Int = 0,

    val audioBookTracksErrorMsg: UiText? = null,
    val isAudioBookTracksLoading: Boolean = false,
    val audioBookTracks: List<AudioBookTracks>? = null,

    val isSummaryLoading: Boolean = false,
    val isSummaryAvailable: Boolean = true,
    val summaryErrorMsg: UiText? = null,
    val summary: String? = null
)
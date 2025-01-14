package org.tawhid.readout.book.openbook.presentation.openbook_detail

import org.tawhid.readout.book.openbook.domain.entity.Book
import org.tawhid.readout.core.utils.UiText

data class BookDetailState(
    val isLoading: Boolean = true,
    val isSaved: Boolean = false,
    val book: Book? = null,

    val isSummaryRequest: Boolean = false,
    val isSummaryLoading: Boolean = false,
    val isSummaryAvailable: Boolean = true,
    val summaryErrorMsg: UiText? = null,
    val summary: String? = null,

    val summaryAudio: String? = null,
    val isSummaryAudioLoading: Boolean = false,
    val summaryAudioErrorMsg: UiText? = null,
)
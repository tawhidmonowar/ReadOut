package org.tawhid.readout.book.summarize.presentation.summarize_home

import org.tawhid.readout.core.utils.UiText

data class SummarizeState(
    val isSummaryLoading: Boolean = false,
    val isSummaryAvailable: Boolean = true,
    val summaryErrorMsg: UiText? = null,
    val summary: String? = null
)
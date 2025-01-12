package org.tawhid.readout.book.summarize.presentation.summarize_home

sealed interface SummarizeAction {
    data object OnBackClick : SummarizeAction
    data object OnSettingClick : SummarizeAction
    data object OnHistoryClick : SummarizeAction
    data class OnSummarizeClick(val title: String, val authors: String? = null, val description: String? = null) : SummarizeAction
}
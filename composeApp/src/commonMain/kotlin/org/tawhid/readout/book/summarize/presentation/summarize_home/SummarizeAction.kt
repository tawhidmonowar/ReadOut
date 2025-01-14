package org.tawhid.readout.book.summarize.presentation.summarize_home

sealed interface SummarizeAction {
    data object OnBackClick : SummarizeAction
    data object OnSettingClick : SummarizeAction
    data class OnSummarizeClick(val title: String, val authors: String? = null, val description: String? = null) : SummarizeAction
}
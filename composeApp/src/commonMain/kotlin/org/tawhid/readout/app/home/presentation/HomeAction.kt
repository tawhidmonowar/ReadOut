package org.tawhid.readout.app.home.presentation

import org.tawhid.readout.book.audiobook.domain.entity.AudioBook
import org.tawhid.readout.book.openbook.domain.Book

sealed interface HomeAction {
    data object OnShowAboutDialog : HomeAction
    data object OnHideAboutDialog : HomeAction
    data object OnSummarizeClick : HomeAction
    data object OnSettingClick : HomeAction
    data object OnLoadAudioBooks : HomeAction
    data object OnLoadTrendingBooks : HomeAction
    data class OnAudioBookClick(val audioBook: AudioBook) : HomeAction
    data class OnBookClick(val book: Book) : HomeAction
}
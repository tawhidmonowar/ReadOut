package org.tawhid.readout.app.home.presentation.recentrelease

import org.tawhid.readout.book.audiobook.domain.entity.AudioBook

sealed interface RecentReleaseAction {
    data class OnBookClick(val book: AudioBook) : RecentReleaseAction
    data object OnBackClick : RecentReleaseAction
}
package org.tawhid.readout.app.home.presentation.recentrelease

import org.tawhid.readout.book.audiobook.domain.entity.AudioBook

data class RecentReleaseState(
    val savedBooks: List<AudioBook> = emptyList()
)
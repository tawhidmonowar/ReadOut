package org.tawhid.readout.app.home.presentation

import org.tawhid.readout.app.home.domain.RecentlyViewedBooks

data class HomeState(
    val showAboutDialog: Boolean = false,
    val recentlyViewedBooks: List<RecentlyViewedBooks> = emptyList(),
)
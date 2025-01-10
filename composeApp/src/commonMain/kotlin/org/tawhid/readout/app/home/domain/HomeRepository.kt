package org.tawhid.readout.app.home.domain

import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    fun getRecentlyViewedBooks(): Flow<List<RecentlyViewedBooks>>
}
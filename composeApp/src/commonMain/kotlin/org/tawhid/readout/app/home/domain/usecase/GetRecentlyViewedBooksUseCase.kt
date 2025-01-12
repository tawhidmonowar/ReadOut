package org.tawhid.readout.app.home.domain.usecase

import kotlinx.coroutines.flow.Flow
import org.tawhid.readout.app.home.domain.entity.RecentlyViewedBooks
import org.tawhid.readout.app.home.domain.repository.HomeRepository

class GetRecentlyViewedBooksUseCase(
    private val homeRepository: HomeRepository
) {
    operator fun invoke(): Flow<List<RecentlyViewedBooks>> {
        return homeRepository.getRecentlyViewedBooks()
    }
}
package org.tawhid.readout.app.home.domain.usecase

import org.tawhid.readout.app.home.domain.repository.HomeRepository
import org.tawhid.readout.book.openbook.domain.Book
import org.tawhid.readout.core.domain.DataError
import org.tawhid.readout.core.domain.Result

class GetTrendingBooksUseCase(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(): Result<List<Book>, DataError.Remote> {
        return homeRepository.getTrendingBooks()
    }
}
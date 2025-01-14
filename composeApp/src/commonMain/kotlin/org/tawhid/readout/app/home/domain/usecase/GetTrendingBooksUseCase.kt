package org.tawhid.readout.app.home.domain.usecase

import org.tawhid.readout.app.home.domain.repository.HomeRepository
import org.tawhid.readout.book.openbook.domain.entity.Book
import org.tawhid.readout.core.utils.DataError
import org.tawhid.readout.core.utils.Result

class GetTrendingBooksUseCase(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(): Result<List<Book>, DataError.Remote> {
        return homeRepository.getTrendingBooks()
    }
}
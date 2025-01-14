package org.tawhid.readout.book.openbook.domain.usecase

import org.tawhid.readout.book.openbook.domain.entity.Book
import org.tawhid.readout.book.openbook.domain.repository.BookRepository
import org.tawhid.readout.core.utils.DataError
import org.tawhid.readout.core.utils.Result

class SearchBooksUseCase(
    private val bookRepository: BookRepository
) {
    suspend operator fun invoke(query: String): Result<List<Book>, DataError> {
        return bookRepository.searchBooksByQuery(query)
    }
}
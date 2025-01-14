package org.tawhid.readout.book.openbook.domain.usecase

import kotlinx.coroutines.flow.Flow
import org.tawhid.readout.book.openbook.domain.entity.Book
import org.tawhid.readout.book.openbook.domain.repository.BookRepository

class GetOpenBookByIdUseCase(
    private val bookRepository: BookRepository
) {
    operator fun invoke(bookId: String): Flow<Pair<Book?, Boolean>> {
        return bookRepository.getBookById(bookId)
    }
}
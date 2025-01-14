package org.tawhid.readout.book.openbook.domain.usecase

import org.tawhid.readout.book.openbook.domain.entity.Book
import org.tawhid.readout.book.openbook.domain.repository.BookRepository
import org.tawhid.readout.core.utils.DataError
import org.tawhid.readout.core.utils.Result

class GetBrowseBooksUseCase(
    private val bookRepository: BookRepository
) {
    suspend operator fun invoke(
        subject: String?,
        offset: Int,
        limit: Int
    ): Result<List<Book>, DataError.Remote> {
        return bookRepository.getBrowseBooks(subject = subject, offset = offset, limit = limit)
    }
}
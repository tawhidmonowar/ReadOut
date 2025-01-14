package org.tawhid.readout.book.openbook.domain.usecase

import org.tawhid.readout.book.openbook.domain.repository.BookRepository
import org.tawhid.readout.core.utils.DataError
import org.tawhid.readout.core.utils.Result

class GetOpenBookSummaryUseCase (
    private val repository: BookRepository
) {
    suspend operator fun invoke(prompt: String, bookId: String): Result<String?, DataError> {
        return repository.getBookSummary(prompt, bookId)
    }
}
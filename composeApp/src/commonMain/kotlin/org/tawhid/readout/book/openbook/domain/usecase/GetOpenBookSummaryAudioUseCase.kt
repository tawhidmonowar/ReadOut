package org.tawhid.readout.book.openbook.domain.usecase

import org.tawhid.readout.book.openbook.domain.repository.BookRepository
import org.tawhid.readout.core.utils.DataError
import org.tawhid.readout.core.utils.Result

class GetOpenBookSummaryAudioUseCase(
    private val bookRepository: BookRepository
) {
    suspend operator fun invoke(summary: String, bookId: String): Result<String?, DataError> {
        return bookRepository.getSummaryAudio(summary, bookId)
    }
}
package org.tawhid.readout.book.summarize.domain.usecase

import org.tawhid.readout.book.summarize.domain.repository.SummarizeRepository
import org.tawhid.readout.core.utils.DataError
import org.tawhid.readout.core.utils.Result

class GetBookSummaryUseCase(
    private val summarizeRepository: SummarizeRepository
) {
    suspend operator fun invoke(prompt: String): Result<String?, DataError> {
        return summarizeRepository.getBookSummary(prompt)
    }
}
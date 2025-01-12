package org.tawhid.readout.book.summarize.domain.repository

import org.tawhid.readout.core.domain.DataError
import org.tawhid.readout.core.domain.Result

interface SummarizeRepository {
    suspend fun getBookSummary(prompt: String): Result<String?, DataError>
}
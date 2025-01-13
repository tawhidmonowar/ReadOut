package org.tawhid.readout.book.summarize.domain.repository

import org.tawhid.readout.core.utils.DataError
import org.tawhid.readout.core.utils.Result

interface SummarizeRepository {
    suspend fun getBookSummary(prompt: String): Result<String?, DataError>
}
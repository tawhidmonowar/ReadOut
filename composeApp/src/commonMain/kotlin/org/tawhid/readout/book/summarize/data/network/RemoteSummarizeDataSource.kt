package org.tawhid.readout.book.summarize.data.network

import org.tawhid.readout.core.domain.DataError
import org.tawhid.readout.core.domain.Result
import org.tawhid.readout.core.gemini.dto.GeminiResponseDto

interface RemoteSummarizeDataSource {
    suspend fun fetchBookSummary(prompt: String): Result<GeminiResponseDto, DataError.Remote>
}
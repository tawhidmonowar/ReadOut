package org.tawhid.readout.book.summarize.data.repository

import org.tawhid.readout.book.summarize.data.network.RemoteSummarizeDataSource
import org.tawhid.readout.book.summarize.domain.repository.SummarizeRepository
import org.tawhid.readout.core.utils.DataError
import org.tawhid.readout.core.utils.Result
import org.tawhid.readout.core.utils.map

class SummarizeRepositoryImpl(
    private val remoteSummarizeDataSource: RemoteSummarizeDataSource
) : SummarizeRepository {
    override suspend fun getBookSummary(prompt: String): Result<String?, DataError> {
        return remoteSummarizeDataSource.fetchBookSummary(prompt).map {
            it.candidates.first().content.parts.first().text
        }
    }
}
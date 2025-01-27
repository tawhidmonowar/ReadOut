package org.tawhid.readout.book.openbook.data.network

import org.tawhid.readout.book.openbook.data.dto.BookWorkDto
import org.tawhid.readout.book.openbook.data.dto.SearchResponseDto
import org.tawhid.readout.book.openbook.data.dto.BrowseResponseDto
import org.tawhid.readout.core.cloudtts.dto.CloudTextToSpeechResponseDto
import org.tawhid.readout.core.utils.DataError
import org.tawhid.readout.core.utils.Result
import org.tawhid.readout.core.gemini.dto.GeminiResponseDto

interface RemoteBookDataSource {
    suspend fun searchBooks(query: String, resultLimit: Int? = null): Result<SearchResponseDto, DataError.Remote>
    suspend fun fetchBrowseBooks(subject: String?, offset: Int? = 0, limit: Int): Result<BrowseResponseDto, DataError.Remote>
    suspend fun fetchBookDescription(bookWorkId: String): Result<BookWorkDto, DataError.Remote>
    suspend fun fetchBookSummary(prompt: String): Result<GeminiResponseDto, DataError.Remote>
    suspend fun fetchBookSummaryAudio(summary: String): Result<CloudTextToSpeechResponseDto, DataError.Remote>
}
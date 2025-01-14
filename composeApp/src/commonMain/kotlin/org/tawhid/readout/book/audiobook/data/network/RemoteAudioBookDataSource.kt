package org.tawhid.readout.book.audiobook.data.network

import org.tawhid.readout.book.audiobook.data.dto.AudioBookTrackResponseDto
import org.tawhid.readout.book.audiobook.data.dto.SearchResponseDto
import org.tawhid.readout.core.gemini.dto.GeminiResponseDto
import org.tawhid.readout.core.utils.DataError
import org.tawhid.readout.core.utils.Result

interface RemoteAudioBookDataSource {
    suspend fun searchAudioBooks(
        query: String,
        resultLimit: Int? = null
    ): Result<SearchResponseDto, DataError.Remote>
    suspend fun fetchBookSummary(prompt: String): Result<GeminiResponseDto, DataError.Remote>
    suspend fun fetchBrowseAudioBooks(genre: String? = null, offset: Int? = 0, limit: Int): Result<SearchResponseDto, DataError.Remote>
    suspend fun fetchAudioBookTracks(audioBookId: String): Result<AudioBookTrackResponseDto, DataError.Remote>
}
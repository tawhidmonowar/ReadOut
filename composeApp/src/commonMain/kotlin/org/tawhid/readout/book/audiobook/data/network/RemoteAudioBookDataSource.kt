package org.tawhid.readout.book.audiobook.data.network

import org.tawhid.readout.book.audiobook.data.dto.AudioBookTrackResponseDto
import org.tawhid.readout.book.audiobook.data.dto.SearchResponseDto
import org.tawhid.readout.core.domain.DataError
import org.tawhid.readout.core.domain.Result

interface RemoteAudioBookDataSource {
    suspend fun searchAudioBooks(
        query: String,
        resultLimit: Int? = null
    ): Result<SearchResponseDto, DataError.Remote>

    suspend fun fetchBrowseAudioBooks(genre: String? = null, page: Int? = 0): Result<SearchResponseDto, DataError.Remote>
    suspend fun fetchAudioBookTracks(audioBookId: String): Result<AudioBookTrackResponseDto, DataError.Remote>
}
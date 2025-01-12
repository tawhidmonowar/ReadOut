package org.tawhid.readout.app.home.data.network

import org.tawhid.readout.book.audiobook.data.dto.SearchResponseDto
import org.tawhid.readout.book.openbook.data.dto.BrowseResponseDto
import org.tawhid.readout.core.domain.DataError
import org.tawhid.readout.core.domain.Result

interface RemoteHomeDataSource {
    suspend fun fetchRecentReleasedAudioBooks(since: Long): Result<SearchResponseDto, DataError.Remote>
    suspend fun fetchTrendingBooks(resultLimit: Int? = null): Result<BrowseResponseDto, DataError.Remote>
}
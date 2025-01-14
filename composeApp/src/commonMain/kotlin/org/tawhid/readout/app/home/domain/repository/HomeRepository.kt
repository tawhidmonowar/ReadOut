package org.tawhid.readout.app.home.domain.repository

import kotlinx.coroutines.flow.Flow
import org.tawhid.readout.book.audiobook.domain.entity.AudioBook
import org.tawhid.readout.book.openbook.domain.entity.Book
import org.tawhid.readout.core.utils.DataError
import org.tawhid.readout.core.utils.Result

interface HomeRepository {
    suspend fun getRecentReleasedAudioBooks(since: Long): Result<List<AudioBook>, DataError.Remote>
    suspend fun getTrendingBooks(): Result<List<Book>, DataError.Remote>
    fun getSavedNewReleaseBooks(): Flow<List<AudioBook>>
}
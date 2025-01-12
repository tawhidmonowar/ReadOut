package org.tawhid.readout.app.home.domain.repository

import kotlinx.coroutines.flow.Flow
import org.tawhid.readout.app.home.domain.entity.RecentlyViewedBooks
import org.tawhid.readout.book.audiobook.domain.AudioBook
import org.tawhid.readout.book.openbook.domain.Book
import org.tawhid.readout.core.domain.DataError
import org.tawhid.readout.core.domain.Result

interface HomeRepository {
    suspend fun getRecentReleasedAudioBooks(since: Long): Result<List<AudioBook>, DataError.Remote>
    suspend fun getTrendingBooks(): Result<List<Book>, DataError.Remote>
    fun getRecentlyViewedBooks(): Flow<List<RecentlyViewedBooks>>
}
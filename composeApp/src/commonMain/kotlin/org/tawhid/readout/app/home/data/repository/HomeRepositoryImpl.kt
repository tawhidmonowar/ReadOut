package org.tawhid.readout.app.home.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import org.tawhid.readout.app.home.data.mappers.toRecentlyViewedBook
import org.tawhid.readout.app.home.data.network.RemoteHomeDataSource
import org.tawhid.readout.app.home.domain.repository.HomeRepository
import org.tawhid.readout.app.home.domain.entity.RecentlyViewedBooks
import org.tawhid.readout.book.audiobook.data.database.AudioBookDao
import org.tawhid.readout.book.audiobook.data.mappers.toAudioBook
import org.tawhid.readout.book.audiobook.domain.AudioBook
import org.tawhid.readout.book.openbook.data.database.OpenBookDao
import org.tawhid.readout.book.openbook.data.mappers.toBook
import org.tawhid.readout.book.openbook.domain.Book
import org.tawhid.readout.core.domain.DataError
import org.tawhid.readout.core.domain.Result
import org.tawhid.readout.core.domain.map

class HomeRepositoryImpl(
    private val remoteHomeDataSource: RemoteHomeDataSource,
    private val openBookDao: OpenBookDao,
    private val audioBookDao: AudioBookDao,
) : HomeRepository {

    override suspend fun getRecentReleasedAudioBooks(since: Long): Result<List<AudioBook>, DataError.Remote> {
        return remoteHomeDataSource.fetchRecentReleasedAudioBooks(since = since).map { dto ->
            dto.results.map {
                it.toAudioBook()
            }
        }
    }

    override suspend fun getTrendingBooks(): Result<List<Book>, DataError.Remote> {
        return remoteHomeDataSource.fetchTrendingBooks().map { dto ->
            dto.results.map { it.toBook() }
        }
    }

    override fun getRecentlyViewedBooks(): Flow<List<RecentlyViewedBooks>> {
        return combine(
            openBookDao.getAllBooks(),
            audioBookDao.getAllBooks()
        ) { openBooks, audioBooks ->

            val latestOpenBooks = openBooks
                .sortedByDescending { it.timeStamp }
                .take(20)
                .map { it.toRecentlyViewedBook() }

            val latestAudioBooks = audioBooks
                .sortedByDescending { it.timeStamp }
                .take(20)
                .map { it.toRecentlyViewedBook() }

            (latestOpenBooks + latestAudioBooks).sortedByDescending { it.timeStamp }
        }
    }
}
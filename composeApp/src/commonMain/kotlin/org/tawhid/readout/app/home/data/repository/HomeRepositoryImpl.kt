package org.tawhid.readout.app.home.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.tawhid.readout.app.home.data.network.RemoteHomeDataSource
import org.tawhid.readout.app.home.domain.repository.HomeRepository
import org.tawhid.readout.book.audiobook.data.database.AudioBookDao
import org.tawhid.readout.book.audiobook.data.mappers.toAudioBook
import org.tawhid.readout.book.audiobook.data.mappers.toRecentlyReleasedAudioBookEntity
import org.tawhid.readout.book.audiobook.domain.entity.AudioBook
import org.tawhid.readout.book.openbook.data.database.OpenBookDao
import org.tawhid.readout.book.openbook.data.mappers.toBook
import org.tawhid.readout.book.openbook.data.mappers.toBookBookEntity
import org.tawhid.readout.book.openbook.domain.Book
import org.tawhid.readout.core.utils.BookType.RECENTLY_RELEASED
import org.tawhid.readout.core.utils.BookType.TRENDING
import org.tawhid.readout.core.utils.DataError
import org.tawhid.readout.core.utils.FORTY_EIGHT_HOURS_IN_MILLIS
import org.tawhid.readout.core.utils.Result
import org.tawhid.readout.core.utils.map
import org.tawhid.readout.core.utils.onSuccess

class HomeRepositoryImpl(
    private val remoteHomeDataSource: RemoteHomeDataSource,
    private val openBookDao: OpenBookDao,
    private val audioBookDao: AudioBookDao,
) : HomeRepository {

    override suspend fun getRecentReleasedAudioBooks(since: Long): Result<List<AudioBook>, DataError.Remote> {
        val currentTime = System.currentTimeMillis()
        return try {
            val existingBooks = audioBookDao.getSavedBooksByType(RECENTLY_RELEASED)
            val isDataStale = existingBooks.firstOrNull()?.timeStamp?.let {
                currentTime - it > FORTY_EIGHT_HOURS_IN_MILLIS
            } ?: true

            if (isDataStale) {
                val fetchedBooksResult =
                    remoteHomeDataSource.fetchRecentReleasedAudioBooks(since).map { dto ->
                        dto.results.map { it.toAudioBook() }
                    }
                fetchedBooksResult.onSuccess { books ->
                    audioBookDao.run {
                        deleteBooksByType(RECENTLY_RELEASED)
                        books.forEach { book ->
                            upsert(book.toRecentlyReleasedAudioBookEntity())
                        }
                    }
                }
                fetchedBooksResult
            } else {
                val audioBooks = existingBooks.map { it.toAudioBook() }
                Result.Success(audioBooks)
            }
        } catch (e: Exception) {
            Result.Error(DataError.Remote.UNKNOWN)
        }
    }

    override suspend fun getTrendingBooks(): Result<List<Book>, DataError.Remote> {
        val currentTime = System.currentTimeMillis()
        return try {
            val existingBooks = openBookDao.getSavedBooksByType(TRENDING)
            val isDataStale = existingBooks.firstOrNull()?.timeStamp?.let {
                currentTime - it > FORTY_EIGHT_HOURS_IN_MILLIS
            } ?: true

            if (isDataStale) {
                val fetchedBooksResult = remoteHomeDataSource.fetchTrendingBooks().map { dto ->
                    dto.results.map { it.toBook() }
                }
                fetchedBooksResult.onSuccess { books ->
                    openBookDao.run {
                        deleteBooksByType(TRENDING)
                        books.forEach { book ->
                            upsert(book.toBookBookEntity(bookType = TRENDING))
                        }
                    }
                }
                fetchedBooksResult
            } else {
                val books = existingBooks.map { it.toBook() }
                Result.Success(books)
            }
        } catch (e: Exception) {
            Result.Error(DataError.Remote.UNKNOWN)
        }
    }

    override fun getSavedNewReleaseBooks(): Flow<List<AudioBook>> {
        return audioBookDao.getSavedNewReleaseBooks(RECENTLY_RELEASED).map { audioBookEntities ->
            audioBookEntities.map { it.toAudioBook() }
        }
    }
}
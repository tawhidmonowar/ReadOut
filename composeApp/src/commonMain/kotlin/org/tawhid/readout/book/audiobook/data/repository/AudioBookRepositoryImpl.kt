package org.tawhid.readout.book.audiobook.data.repository

import androidx.sqlite.SQLiteException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.tawhid.readout.book.audiobook.data.database.AudioBookDao
import org.tawhid.readout.book.audiobook.data.mappers.toAudioBook
import org.tawhid.readout.book.audiobook.data.mappers.toAudioBookEntity
import org.tawhid.readout.book.audiobook.data.mappers.toAudioBookTracks
import org.tawhid.readout.book.audiobook.data.network.RemoteAudioBookDataSource
import org.tawhid.readout.book.audiobook.domain.AudioBook
import org.tawhid.readout.book.audiobook.domain.AudioBookRepository
import org.tawhid.readout.book.audiobook.domain.AudioBookTrack
import org.tawhid.readout.core.domain.DataError
import org.tawhid.readout.core.domain.EmptyResult
import org.tawhid.readout.core.domain.Result
import org.tawhid.readout.core.domain.map

class AudioBookRepositoryImpl(
    private val remoteAudioBookDataSource: RemoteAudioBookDataSource,
    private val audioBookDao: AudioBookDao
) : AudioBookRepository {

    override suspend fun searchAudioBooks(query: String): Result<List<AudioBook>, DataError.Remote> {
        return remoteAudioBookDataSource.searchAudioBooks(query).map { dto ->
            dto.results.map {
                it.toAudioBook()
            }
        }
    }

    override suspend fun getBrowseAudioBooks(
        genre: String?,
        page: Int?
    ): Result<List<AudioBook>, DataError.Remote> {
        return remoteAudioBookDataSource.fetchBrowseAudioBooks(genre = genre, page = page)
            .map { dto ->
                dto.results.map {
                    it.toAudioBook()
                }
            }
    }

    override suspend fun getAudioBookTracks(audioBookId: String): Result<List<AudioBookTrack>, DataError.Remote> {
        return remoteAudioBookDataSource.fetchAudioBookTracks(audioBookId).map { dto ->
            dto.audioBookTracks.map {
                it.toAudioBookTracks()
            }
        }
    }

    override suspend fun saveBook(book: AudioBook): EmptyResult<DataError.Local> {
        return try {
            audioBookDao.upsert(book.toAudioBookEntity())
            Result.Success(Unit)
        } catch (e: SQLiteException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override fun getSavedBooks(): Flow<List<AudioBook>> {
        return audioBookDao.getSavedBooks().map { audioBookEntities ->
            audioBookEntities.map { it.toAudioBook() }
        }
    }
}
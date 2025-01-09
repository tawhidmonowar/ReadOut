package org.tawhid.readout.book.audiobook.data.repository

import org.tawhid.readout.book.audiobook.data.mappers.toAudioBook
import org.tawhid.readout.book.audiobook.data.mappers.toAudioBookTracks
import org.tawhid.readout.book.audiobook.data.network.RemoteAudioBookDataSource
import org.tawhid.readout.book.audiobook.domain.AudioBook
import org.tawhid.readout.book.audiobook.domain.AudioBookRepository
import org.tawhid.readout.book.audiobook.domain.AudioBookTrack
import org.tawhid.readout.core.domain.DataError
import org.tawhid.readout.core.domain.Result
import org.tawhid.readout.core.domain.map

class AudioBookRepositoryImpl(
    private val remoteAudioBookDataSource: RemoteAudioBookDataSource
) : AudioBookRepository {

    override suspend fun searchAudioBooks(query: String): Result<List<AudioBook>, DataError.Remote> {
        return remoteAudioBookDataSource.searchAudioBooks(query).map { dto ->
            dto.results.map {
                it.toAudioBook()
            }
        }
    }

    override suspend fun getBrowseAudioBooks(): Result<List<AudioBook>, DataError.Remote> {
        return remoteAudioBookDataSource.fetchBrowseAudioBooks().map { dto ->
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
}
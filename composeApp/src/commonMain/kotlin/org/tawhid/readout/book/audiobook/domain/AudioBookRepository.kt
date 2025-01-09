package org.tawhid.readout.book.audiobook.domain

import org.tawhid.readout.core.domain.DataError
import org.tawhid.readout.core.domain.Result

interface AudioBookRepository {
    suspend fun searchAudioBooks(query: String): Result<List<AudioBook>, DataError.Remote>
    suspend fun getBrowseAudioBooks(): Result<List<AudioBook>, DataError.Remote>
    suspend fun getAudioBookTracks(audioBookId: String): Result<List<AudioBookTrack>, DataError.Remote>
}
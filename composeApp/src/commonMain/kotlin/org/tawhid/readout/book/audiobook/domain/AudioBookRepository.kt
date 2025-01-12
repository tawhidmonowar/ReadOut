package org.tawhid.readout.book.audiobook.domain

import kotlinx.coroutines.flow.Flow
import org.tawhid.readout.book.openbook.domain.Book
import org.tawhid.readout.core.domain.DataError
import org.tawhid.readout.core.domain.EmptyResult
import org.tawhid.readout.core.domain.Result

interface AudioBookRepository {
    suspend fun searchAudioBooks(query: String): Result<List<AudioBook>, DataError.Remote>
    suspend fun getBrowseAudioBooks(genre: String? = null, offset: Int? = 0, limit: Int): Result<List<AudioBook>, DataError.Remote>
    suspend fun getAudioBookTracks(audioBookId: String): Result<List<AudioBookTrack>, DataError.Remote>
    suspend fun saveBook(book: AudioBook): EmptyResult<DataError.Local>
    fun getSavedBooks(): Flow<List<AudioBook>>
}
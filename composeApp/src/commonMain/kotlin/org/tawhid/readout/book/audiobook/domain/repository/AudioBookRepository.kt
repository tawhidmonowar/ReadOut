package org.tawhid.readout.book.audiobook.domain.repository

import kotlinx.coroutines.flow.Flow
import org.tawhid.readout.book.audiobook.domain.entity.AudioBook
import org.tawhid.readout.book.audiobook.domain.entity.AudioBookTrack
import org.tawhid.readout.core.utils.DataError
import org.tawhid.readout.core.utils.EmptyResult
import org.tawhid.readout.core.utils.Result

interface AudioBookRepository {
    suspend fun searchAudioBooks(query: String): Result<List<AudioBook>, DataError.Remote>
    suspend fun getBrowseAudioBooks(genre: String? = null, offset: Int? = 0, limit: Int): Result<List<AudioBook>, DataError.Remote>
    suspend fun getAudioBookTracks(audioBookId: String): Result<List<AudioBookTrack>, DataError.Remote>
    suspend fun saveBook(book: AudioBook): EmptyResult<DataError.Local>
    suspend fun getBookSummary(prompt: String, bookId: String): Result<String?, DataError>
    suspend fun insertBookIntoDB(book: AudioBook): EmptyResult<DataError.Local>
    suspend fun deleteFromSaved(id: String)
    fun getSavedBooks(): Flow<List<AudioBook>>
    fun getBookById(id: String): Flow<Pair<AudioBook?, Boolean>>
}
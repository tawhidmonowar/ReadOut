package org.tawhid.readout.book.openbook.domain.repository

import kotlinx.coroutines.flow.Flow
import org.tawhid.readout.book.audiobook.domain.entity.AudioBook
import org.tawhid.readout.book.openbook.domain.entity.Book
import org.tawhid.readout.core.utils.DataError
import org.tawhid.readout.core.utils.EmptyResult
import org.tawhid.readout.core.utils.Result

interface BookRepository {
    suspend fun searchBooksByQuery(query: String): Result<List<Book>, DataError.Remote>
    suspend fun getBookDescriptionById(bookId: String): Result<String?, DataError>
    suspend fun getBrowseBooks(subject: String?, offset: Int? = 0, limit: Int): Result<List<Book>, DataError.Remote>
    suspend fun getBookSummary(prompt: String, bookId: String): Result<String?, DataError>
    suspend fun getSummaryAudio(summary: String, bookId: String): Result<String?, DataError>
    suspend fun insertBookIntoDB(book: Book): EmptyResult<DataError.Local>
    suspend fun updateIsSaved(book: Book, isSaved: Boolean, currentTime: Long): EmptyResult<DataError.Local>
    suspend fun saveBook(book: Book): EmptyResult<DataError.Local>
    suspend fun deleteFromSaved(id: String)
    fun getSavedBooks(): Flow<List<Book>>
    fun getBookById(id: String): Flow<Pair<Book?, Boolean>>
}
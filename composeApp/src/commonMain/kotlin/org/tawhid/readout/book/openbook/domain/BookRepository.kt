package org.tawhid.readout.book.openbook.domain

import org.tawhid.readout.core.domain.DataError
import org.tawhid.readout.core.domain.Result

interface BookRepository {
    suspend fun searchBooksByQuery(query: String): Result<List<Book>, DataError.Remote>
    suspend fun getTrendingBooks(): Result<List<Book>, DataError.Remote>
    suspend fun getBookDescriptionById(bookId: String): Result<String?, DataError>
    suspend fun getBrowseBooks(subject: String,resultLimit: Int? = null, offset: Int? = null): Result<List<Book>, DataError.Remote>
    suspend fun getBookSummary(prompt: String): Result<String?, DataError>
    suspend fun getSummaryAudio(summary: String): Result<String?, DataError>
}
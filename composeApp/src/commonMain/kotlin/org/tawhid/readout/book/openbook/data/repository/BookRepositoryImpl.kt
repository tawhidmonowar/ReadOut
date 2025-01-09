package org.tawhid.readout.book.openbook.data.repository

import androidx.sqlite.SQLiteException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.tawhid.readout.book.openbook.data.database.OpenBookDao
import org.tawhid.readout.book.openbook.data.mappers.toBook
import org.tawhid.readout.book.openbook.data.mappers.toBookBookEntity
import org.tawhid.readout.book.openbook.data.network.RemoteBookDataSource
import org.tawhid.readout.book.openbook.domain.Book
import org.tawhid.readout.book.openbook.domain.BookRepository
import org.tawhid.readout.core.domain.DataError
import org.tawhid.readout.core.domain.EmptyResult
import org.tawhid.readout.core.domain.Result
import org.tawhid.readout.core.domain.map

class BookRepositoryImpl(
    private val remoteBookDataSource: RemoteBookDataSource,
    private val openBookDao: OpenBookDao,
) : BookRepository {

    override suspend fun searchBooksByQuery(query: String): Result<List<Book>, DataError.Remote> {
        return remoteBookDataSource
            .searchBooks(query)
            .map { dto ->
                dto.results.map { it.toBook() }
            }
    }

    override suspend fun getTrendingBooks(): Result<List<Book>, DataError.Remote> {
        return remoteBookDataSource.fetchTrendingBooks(
            resultLimit = 102
        )
            .map { dto ->
                dto.results.map { it.toBook() }
            }
    }

    override suspend fun getBookDescriptionById(bookId: String): Result<String?, DataError> {
        //val localResult = saveBookDao.getSavedBook(bookId)
        val localResult = null
        return if (localResult == null) {
            remoteBookDataSource
                .fetchBookDescription(bookId)
                .map { it.description }
        } else {
            Result.Success(localResult)
        }
    }

    override suspend fun getBrowseBooks(
        subject: String,
        resultLimit: Int?,
        offset: Int?
    ): Result<List<Book>, DataError.Remote> {
        return remoteBookDataSource.fetchBrowseBooks(
            subject = subject,
            resultLimit = resultLimit,
            offset = offset
        ).map { dto ->
            dto.results.map { it.toBook() }
        }
    }

    override suspend fun getBookSummary(prompt: String): Result<String?, DataError> {
        return remoteBookDataSource
            .fetchBookSummary(prompt)
            .map { it.candidates.first().content.parts.first().text }
    }

    override suspend fun getSummaryAudio(summary: String): Result<String?, DataError> {
        return remoteBookDataSource.fetchBookSummaryAudio(summary).map {
            it.audioContent
        }
    }

    override suspend fun saveBook(book: Book): EmptyResult<DataError.Local> {
        return try {
            openBookDao.upsert(book.toBookBookEntity())
            Result.Success(Unit)
        } catch (e: SQLiteException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override fun getSavedBooks(): Flow<List<Book>> {
        return openBookDao.getSavedBooks().map { bookEntities ->
            bookEntities.map { it.toBook() }
        }
    }
}
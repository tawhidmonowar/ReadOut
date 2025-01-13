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
import org.tawhid.readout.core.utils.DataError
import org.tawhid.readout.core.utils.EmptyResult
import org.tawhid.readout.core.utils.Result
import org.tawhid.readout.core.utils.map

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
        subject: String?,
        offset: Int?,
        limit: Int
    ): Result<List<Book>, DataError.Remote> {
        return remoteBookDataSource.fetchBrowseBooks(
            subject = subject,
            offset = offset,
            limit = limit
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

    override suspend fun insertBookIntoDB(book: Book): EmptyResult<DataError.Local> {
        return try {
            openBookDao.upsert(book.toBookBookEntity())
            Result.Success(Unit)
        } catch (e: SQLiteException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun updateIsSaved(
        book: Book,
        isSaved: Boolean,
        currentTime: Long
    ): EmptyResult<DataError.Local> {
        return try {
            openBookDao.updateIsSaved(id = book.id, isSaved = isSaved, timeStamp = currentTime)
            Result.Success(Unit)
        } catch (e: SQLiteException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override fun getSavedBooks(): Flow<List<Book>> {
        return openBookDao.getSavedBooks()
            .map { bookEntities ->
                bookEntities.sortedByDescending { it.timeStamp }.map { it.toBook() }
            }
    }
}
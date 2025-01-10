package org.tawhid.readout.app.home.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import org.tawhid.readout.app.home.data.mappers.toRecentlyViewedBook
import org.tawhid.readout.app.home.domain.HomeRepository
import org.tawhid.readout.app.home.domain.RecentlyViewedBooks
import org.tawhid.readout.book.audiobook.data.database.AudioBookDao
import org.tawhid.readout.book.openbook.data.database.OpenBookDao

class HomeRepositoryImpl(
    private val openBookDao: OpenBookDao,
    private val audioBookDao: AudioBookDao,
) : HomeRepository {
    override fun getRecentlyViewedBooks(): Flow<List<RecentlyViewedBooks>> {
        return combine(
            openBookDao.getSavedBooks(),
            audioBookDao.getSavedBooks()
        ) { openBooks, audioBooks ->
            val mappedOpenBooks = openBooks.take(10).map { it.toRecentlyViewedBook() }
            val mappedAudioBooks = audioBooks.take(10).map { it.toRecentlyViewedBook() }
            (mappedOpenBooks + mappedAudioBooks)
        }
    }
}
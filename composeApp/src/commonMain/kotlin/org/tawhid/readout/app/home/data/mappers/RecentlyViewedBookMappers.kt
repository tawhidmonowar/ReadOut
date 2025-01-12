package org.tawhid.readout.app.home.data.mappers

import org.tawhid.readout.app.home.domain.entity.RecentlyViewedBooks
import org.tawhid.readout.book.audiobook.data.database.AudioBookEntity
import org.tawhid.readout.book.openbook.data.database.OpenBookEntity
import org.tawhid.readout.core.utils.BookType

fun OpenBookEntity.toRecentlyViewedBook(): RecentlyViewedBooks {
    return RecentlyViewedBooks(
        id = id,
        title = title,
        imgUrl = imgUrl,
        authors = authors,
        description = description,
        languages = languages.toString(),
        timeStamp = timeStamp,
        type = BookType.OPEN_BOOK
    )
}

fun AudioBookEntity.toRecentlyViewedBook(): RecentlyViewedBooks {
    return RecentlyViewedBooks(
        id = id,
        title = title,
        imgUrl = imgUrl,
        authors = authors,
        description = description,
        languages = language,
        timeStamp = timeStamp,
        type = BookType.AUDIO_BOOK
    )
}
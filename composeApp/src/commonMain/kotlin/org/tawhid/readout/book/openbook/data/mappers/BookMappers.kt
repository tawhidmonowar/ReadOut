package org.tawhid.readout.book.openbook.data.mappers

import org.tawhid.readout.book.openbook.data.database.OpenBookEntity
import org.tawhid.readout.book.openbook.data.dto.SearchedBookDto
import org.tawhid.readout.book.openbook.domain.Book

fun SearchedBookDto.toBook(): Book {
    val authorNames2ndApproach = authors?.map { it.name }
    return Book(
        id = id.substringAfterLast("/"),
        title = title,
        imgUrl = if (coverKey != null) {
            "https://covers.openlibrary.org/b/olid/${coverKey}-L.jpg"
        } else {
            "https://covers.openlibrary.org/b/id/${coverAlternativeKey}-L.jpg"
        },
        authors = authorNames ?: authorNames2ndApproach ?: emptyList(),
        description = null,
        languages = languages ?: emptyList(),
        firstPublishYear = firstPublishYear.toString(),
        avgRating = ratingsAverage ?: 0.0,
        ratingCount = ratingsCount ?: 0,
        numPages = numPagesMedian ?: 0,
        numEditions = numEditions ?: 0
    )
}

fun Book.toBookBookEntity(
    bookType: String? = null
): OpenBookEntity {
    return OpenBookEntity(
        id = id,
        title = title,
        imgUrl = imgUrl,
        authors = authors,
        description = description,
        languages = languages,
        firstPublishYear = firstPublishYear,
        avgRating = avgRating,
        ratingCount = ratingCount,
        numPages = numPages,
        numEditions = numEditions,
        bookType = bookType,
        isSaved = null,
        isViewed = null,
        summaryText = null,
        summaryBase64 = null,
        timeStamp = System.currentTimeMillis(),
    )
}

fun OpenBookEntity.toBook(): Book {
    return Book(
        id = id,
        title = title,
        imgUrl = imgUrl,
        authors = authors,
        description = description,
        languages = languages,
        firstPublishYear = firstPublishYear,
        avgRating = avgRating,
        ratingCount = ratingCount,
        numPages = numPages,
        numEditions = numEditions
    )
}
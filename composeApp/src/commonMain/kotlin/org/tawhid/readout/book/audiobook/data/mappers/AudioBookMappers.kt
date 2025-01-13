package org.tawhid.readout.book.audiobook.data.mappers

import org.tawhid.readout.book.audiobook.data.database.AudioBookEntity
import org.tawhid.readout.book.audiobook.data.dto.AudioBookTrackDto
import org.tawhid.readout.book.audiobook.data.dto.SearchedAudioBookDto
import org.tawhid.readout.book.audiobook.domain.entity.AudioBook
import org.tawhid.readout.book.audiobook.domain.entity.AudioBookTrack

fun SearchedAudioBookDto.toAudioBook(): AudioBook {
    return AudioBook(
        id = id,
        title = title,
        description = description,
        authors = authors?.map { it.firstName + " " + it.lastName } ?: emptyList(),
        language = language,
        copyrightYear = copyrightYear,
        numSections = numSections,
        textSourceUrl = textSourceUrl,
        zipFileUrl = zipFileUrl,
        libriVoxUrl = libriVoxUrl,
        totalTime = totalTime,
        imgUrl = coverImg
    )
}

fun AudioBookTrackDto.toAudioBookTracks(): AudioBookTrack {
    return AudioBookTrack(
        id = id,
        sectionNumber = sectionNumber,
        title = title,
        listenUrl = listenUrl,
        language = language,
        playtime = playtime,
    )
}

fun AudioBookEntity.toAudioBook(): AudioBook {
    return AudioBook(
        id = id,
        title = title ?: "Title Not Found",
        description = description,
        language = language,
        copyrightYear = copyrightYear,
        numSections = numSections,
        textSourceUrl = textSourceUrl,
        zipFileUrl = zipFileUrl,
        libriVoxUrl = libriVoxUrl,
        totalTime = totalTime,
        imgUrl = imgUrl,
        authors = authors,
    )
}

fun AudioBook.toAudioBookEntity(): AudioBookEntity {
    return AudioBookEntity(
        id = id,
        title = title,
        description = description,
        language = language,
        copyrightYear = copyrightYear,
        numSections = numSections,
        textSourceUrl = textSourceUrl,
        zipFileUrl = zipFileUrl,
        libriVoxUrl = libriVoxUrl,
        totalTime = totalTime,
        imgUrl = imgUrl,
        authors = authors,
        audioBookTracks = null,
        bookType = null,
        isSaved = null,
        isViewed = null,
        summaryText = null,
        summaryBase64 = null,
        timeStamp = System.currentTimeMillis()
    )
}
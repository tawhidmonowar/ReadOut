package org.tawhid.readout.book.audiobook.data.mappers

import org.tawhid.readout.book.audiobook.data.dto.AudioBookTrackDto
import org.tawhid.readout.book.audiobook.data.dto.SearchedAudioBookDto
import org.tawhid.readout.book.audiobook.domain.AudioBook
import org.tawhid.readout.book.audiobook.domain.AudioBookTrack

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
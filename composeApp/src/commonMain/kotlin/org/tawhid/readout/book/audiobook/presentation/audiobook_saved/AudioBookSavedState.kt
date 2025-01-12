package org.tawhid.readout.book.audiobook.presentation.audiobook_saved

import org.tawhid.readout.book.audiobook.domain.AudioBook

data class AudioBookSavedState(
    val savedBooks: List<AudioBook> = emptyList()
)
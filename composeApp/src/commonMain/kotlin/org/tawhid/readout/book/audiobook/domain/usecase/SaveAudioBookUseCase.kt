package org.tawhid.readout.book.audiobook.domain.usecase

import org.tawhid.readout.book.audiobook.domain.entity.AudioBook
import org.tawhid.readout.book.audiobook.domain.repository.AudioBookRepository

class SaveAudioBookUseCase(
    private val audioBookRepository: AudioBookRepository
) {
    suspend operator fun invoke(audioBook: AudioBook) {
        audioBookRepository.saveBook(audioBook)
    }
}
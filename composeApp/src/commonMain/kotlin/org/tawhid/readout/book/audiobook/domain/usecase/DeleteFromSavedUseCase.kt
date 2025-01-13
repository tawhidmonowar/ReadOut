package org.tawhid.readout.book.audiobook.domain.usecase

import org.tawhid.readout.book.audiobook.domain.repository.AudioBookRepository

class DeleteFromSavedUseCase(
    private val audioBookRepository: AudioBookRepository
) {
    suspend operator fun invoke(audioBookId: String) {
        audioBookRepository.deleteFromSaved(audioBookId)
    }
}
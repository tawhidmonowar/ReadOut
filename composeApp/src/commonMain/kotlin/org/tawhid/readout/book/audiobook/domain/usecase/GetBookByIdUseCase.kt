package org.tawhid.readout.book.audiobook.domain.usecase

import kotlinx.coroutines.flow.Flow
import org.tawhid.readout.book.audiobook.domain.entity.AudioBook
import org.tawhid.readout.book.audiobook.domain.repository.AudioBookRepository

class GetBookByIdUseCase(
    private val audioBookRepository: AudioBookRepository
) {
    operator fun invoke(audioBookId: String): Flow<Pair<AudioBook?, Boolean>> {
        return audioBookRepository.getBookById(audioBookId)
    }
}
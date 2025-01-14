package org.tawhid.readout.book.audiobook.domain.usecase

import org.tawhid.readout.book.audiobook.domain.entity.AudioBook
import org.tawhid.readout.book.audiobook.domain.repository.AudioBookRepository
import org.tawhid.readout.core.utils.DataError
import org.tawhid.readout.core.utils.Result

class SearchAudioBooksUseCase(
    private val audioBookRepository: AudioBookRepository
) {
    suspend operator fun invoke(query: String): Result<List<AudioBook>, DataError> {
        return audioBookRepository.searchAudioBooks(query)
    }
}
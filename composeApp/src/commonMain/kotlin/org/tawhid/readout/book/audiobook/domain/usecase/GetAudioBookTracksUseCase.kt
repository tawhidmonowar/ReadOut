package org.tawhid.readout.book.audiobook.domain.usecase

import org.tawhid.readout.book.audiobook.domain.entity.AudioBookTrack
import org.tawhid.readout.book.audiobook.domain.repository.AudioBookRepository
import org.tawhid.readout.core.utils.DataError
import org.tawhid.readout.core.utils.Result

class GetAudioBookTracksUseCase(
    private val audioBookRepository: AudioBookRepository
) {
    suspend operator fun invoke(audioBookId: String): Result<List<AudioBookTrack>, DataError.Remote> {
        return audioBookRepository.getAudioBookTracks(audioBookId)
    }
}
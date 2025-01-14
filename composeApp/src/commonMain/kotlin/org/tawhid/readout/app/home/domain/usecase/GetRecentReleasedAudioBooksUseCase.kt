package org.tawhid.readout.app.home.domain.usecase

import org.tawhid.readout.app.home.domain.repository.HomeRepository
import org.tawhid.readout.book.audiobook.domain.entity.AudioBook
import org.tawhid.readout.core.utils.DataError
import org.tawhid.readout.core.utils.Result
import java.util.Calendar

class GetRecentReleasedAudioBooksUseCase(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(): Result<List<AudioBook>, DataError.Remote> {
        return homeRepository.getRecentReleasedAudioBooks(getTwoMonthsAgoTimestamp())
    }
}

fun getTwoMonthsAgoTimestamp(): Long {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.MONTH, -2)
    return calendar.timeInMillis / 1000
}
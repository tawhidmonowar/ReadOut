package org.tawhid.readout.book.audiobook.presentation.audiobook_saved

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import org.tawhid.readout.book.audiobook.domain.AudioBookRepository

class AudioBookSavedViewModel(
    private val audioBookRepository: AudioBookRepository
) : ViewModel() {

    private var observeSaveJob: Job? = null
    private val _state = MutableStateFlow(AudioBookSavedState())

    val state = _state.onStart {
        observeSavedBooks()
    }.stateIn(
        viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = _state.value
    )

    private fun observeSavedBooks() {
        observeSaveJob?.cancel()
        observeSaveJob = audioBookRepository.getSavedBooks().onEach { savedBooks ->
            _state.update {
                it.copy(
                    savedBooks = savedBooks
                )
            }
        }.launchIn(viewModelScope)
    }
}
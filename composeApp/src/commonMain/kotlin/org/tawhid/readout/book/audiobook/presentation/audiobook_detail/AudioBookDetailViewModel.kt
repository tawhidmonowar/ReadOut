package org.tawhid.readout.book.audiobook.presentation.audiobook_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.tawhid.readout.app.navigation.Route
import org.tawhid.readout.book.audiobook.domain.AudioBookRepository
import org.tawhid.readout.core.domain.onError
import org.tawhid.readout.core.domain.onSuccess
import org.tawhid.readout.core.utils.toUiText

class AudioBookDetailViewModel(
    private val audioBookRepository: AudioBookRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val audioBookId = savedStateHandle.toRoute<Route.AudioBookDetail>().id
    private val _state = MutableStateFlow(AudioBookDetailState())

    val state = _state.onStart {
        getAudioBookTracks()
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        _state.value
    )

    fun onAction(action: AudioBookDetailAction) {
        when (action) {
            is AudioBookDetailAction.OnSelectedBookChange -> {
                _state.update {
                    it.copy(
                        audioBook = action.audioBook
                    )
                }
            }

            is AudioBookDetailAction.OnTabSelected -> {
                _state.update {
                    it.copy(selectedTabIndex = action.index)
                }
            }

            is AudioBookDetailAction.OnSaveClick -> {
                viewModelScope.launch {
                    state.value.audioBook?.let { book ->
                        audioBookRepository.saveBook(book)
                    }
                }
            }

            else -> Unit
        }
    }

    private fun getAudioBookTracks() = viewModelScope.launch {
        _state.update {
            it.copy(
                isAudioBookTracksLoading = true
            )
        }
        if (audioBookId != null) {
            audioBookRepository.getAudioBookTracks(audioBookId = audioBookId)
                .onSuccess { audioBookTracks ->
                    _state.update {
                        it.copy(
                            isAudioBookTracksLoading = false,
                            audioBookTracks = audioBookTracks
                        )
                    }
                }.onError { error ->
                    _state.update {
                        it.copy(
                            isAudioBookTracksLoading = false,
                            audioBookTracksErrorMsg = error.toUiText()
                        )
                    }
                }
        }
    }
}
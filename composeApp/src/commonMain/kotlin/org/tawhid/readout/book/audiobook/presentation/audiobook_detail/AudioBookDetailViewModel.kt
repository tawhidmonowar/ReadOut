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
import org.tawhid.readout.book.audiobook.domain.usecase.DeleteFromSavedUseCase
import org.tawhid.readout.book.audiobook.domain.usecase.GetAudioBookTracksUseCase
import org.tawhid.readout.book.audiobook.domain.usecase.GetSavedBookByIdUseCase
import org.tawhid.readout.book.audiobook.domain.usecase.SaveAudioBookUseCase
import org.tawhid.readout.core.utils.onError
import org.tawhid.readout.core.utils.onSuccess
import org.tawhid.readout.core.utils.toUiText

class AudioBookDetailViewModel(
    private val getAudioBookTracksUseCase: GetAudioBookTracksUseCase,
    private val getSavedBookByIdUseCase: GetSavedBookByIdUseCase,
    private val deleteFromSavedUseCase: DeleteFromSavedUseCase,
    private val saveAudioBookUseCase: SaveAudioBookUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val audioBookId = savedStateHandle.toRoute<Route.AudioBookDetail>().id
    private val _state = MutableStateFlow(AudioBookDetailState())
    val state = _state.onStart {
        getAudioBookTracks()
        observeSavedStatus()
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
                if (state.value.isSaved) {
                    deleteFromSaved()
                } else {
                    saveAudioBook()
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
        audioBookId?.let { audioBookId ->
            getAudioBookTracksUseCase(audioBookId).onSuccess { audioBookTracks ->
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

    private fun saveAudioBook() = viewModelScope.launch {
        state.value.audioBook?.let { book ->
            saveAudioBookUseCase(book)
        }
    }

    private fun deleteFromSaved() = viewModelScope.launch {
        audioBookId?.let { audioBookId ->
            deleteFromSavedUseCase(audioBookId)
        }
    }

    private fun observeSavedStatus() = viewModelScope.launch {
        audioBookId?.let { audioBookId ->
            getSavedBookByIdUseCase(audioBookId).collect { audioBook ->
                if (audioBook != null) {
                    _state.update {
                        it.copy(
                            isSaved = true,
                            audioBook = audioBook
                        )
                    }
                } else {
                    _state.update {
                        it.copy(
                            isSaved = false,
                        )
                    }
                }
            }
        }
    }
}
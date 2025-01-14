package org.tawhid.readout.app.home.presentation.recentrelease

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
import org.tawhid.readout.app.home.domain.usecase.GetSavedRecentReleaseBooksUseCase

class RecentReleaseViewModel(
    private val getSavedRecentReleaseBooksUseCase: GetSavedRecentReleaseBooksUseCase
) : ViewModel() {
    private var observeSaveJob: Job? = null
    private val _state = MutableStateFlow(RecentReleaseState())

    val state = _state.onStart {
        observeSavedBooks()
    }.stateIn(
        viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = _state.value
    )

    private fun observeSavedBooks() {
        observeSaveJob?.cancel()
        observeSaveJob = getSavedRecentReleaseBooksUseCase().onEach { savedBooks ->
            _state.update {
                it.copy(
                    savedBooks = savedBooks
                )
            }
        }.launchIn(viewModelScope)
    }
}
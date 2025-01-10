package org.tawhid.readout.app.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import org.tawhid.readout.app.home.domain.HomeRepository

class HomeViewModel(
    private val homeRepository: HomeRepository
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    private var observeSaveJob: Job? = null
    val state = _state
        .onStart {
            observeSavedBooks()
        }
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = _state.value
        )

    fun onAction(action: HomeAction) {
        when (action) {
            is HomeAction.OnShowAboutInfoDialog -> {
                _state.update {
                    it.copy(
                        showAboutDialog = true
                    )
                }
            }

            is HomeAction.OnHideAboutInfoDialog -> {
                _state.update {
                    it.copy(
                        showAboutDialog = false
                    )
                }
            }

            else -> Unit
        }
    }

    private fun observeSavedBooks() {
        observeSaveJob?.cancel()
        observeSaveJob = homeRepository.getRecentlyViewedBooks()
            .map { savedBooks ->
                savedBooks.sortedByDescending { it.timeStamp }
            }
            .onEach { recentlyPlayedBooks ->
                _state.update {
                    it.copy(
                        recentlyViewedBooks = recentlyPlayedBooks
                    )
                }
            }
            .launchIn(viewModelScope)
    }

}
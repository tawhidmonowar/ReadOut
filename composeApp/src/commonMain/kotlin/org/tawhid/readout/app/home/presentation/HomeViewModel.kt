package org.tawhid.readout.app.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.tawhid.readout.app.home.domain.usecase.GetRecentReleasedAudioBooksUseCase
import org.tawhid.readout.app.home.domain.usecase.GetTrendingBooksUseCase
import org.tawhid.readout.core.utils.onError
import org.tawhid.readout.core.utils.onSuccess
import org.tawhid.readout.core.utils.toUiText

class HomeViewModel(
    private val getRecentReleasedAudioBooksUseCase: GetRecentReleasedAudioBooksUseCase,
    private val getTrendingBooksUseCase: GetTrendingBooksUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state
        .onStart {
            recentlyReleasedAudioBooks()
            trendingBooks()
        }
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = _state.value
        )

    fun onAction(action: HomeAction) {
        when (action) {
            is HomeAction.OnShowAboutDialog -> {
                _state.update {
                    it.copy(
                        showAboutDialog = true
                    )
                }
            }

            is HomeAction.OnHideAboutDialog -> {
                _state.update {
                    it.copy(
                        showAboutDialog = false
                    )
                }
            }

            is HomeAction.OnLoadAudioBooks -> {
                recentlyReleasedAudioBooks()
            }

            is HomeAction.OnLoadTrendingBooks -> {
                trendingBooks()
            }

            else -> Unit
        }
    }

    private fun recentlyReleasedAudioBooks() = viewModelScope.launch {
        _state.update {
            it.copy(
                isAudioBooksLoading = true,
                audioBooksErrorMsg = null
            )
        }
        getRecentReleasedAudioBooksUseCase().onSuccess { audioBooks ->
            _state.update {
                it.copy(
                    isAudioBooksLoading = false,
                    audioBooksErrorMsg = null,
                    recentlyReleasedAudioBooks = audioBooks
                )
            }
        }.onError { error ->
            _state.update {
                it.copy(
                    isAudioBooksLoading = false,
                    audioBooksErrorMsg = error.toUiText()
                )
            }
        }
    }

    private fun trendingBooks() = viewModelScope.launch {
        _state.update {
            it.copy(
                isTrendingLoading = true,
                trendingErrorMsg = null
            )
        }
        getTrendingBooksUseCase().onSuccess { books ->
            _state.update {
                it.copy(
                    isTrendingLoading = false,
                    trendingErrorMsg = null,
                    trendingBooks = books
                )
            }
        }.onError { error ->
            _state.update {
                it.copy(
                    isTrendingLoading = false,
                    trendingErrorMsg = error.toUiText()
                )
            }
        }
    }
}
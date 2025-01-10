package org.tawhid.readout.book.audiobook.presentation.audiobook_home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.tawhid.readout.book.audiobook.domain.AudioBook
import org.tawhid.readout.book.audiobook.domain.AudioBookRepository
import org.tawhid.readout.core.domain.onError
import org.tawhid.readout.core.domain.onSuccess
import org.tawhid.readout.core.utils.SEARCH_TRIGGER_CHAR
import org.tawhid.readout.core.utils.toUiText

class AudioBookHomeViewModel(
    private val audioBookRepository: AudioBookRepository
) : ViewModel() {

    private val cachedBooks = emptyList<AudioBook>()
    private var searchJob: Job? = null
    private var observeSaveJob: Job? = null

    private val _state = MutableStateFlow(AudioBookHomeState())
    val state = _state.onStart {

        if (cachedBooks.isEmpty()) {
            observeSearchQuery()
        }

        observeSavedBooks()
        getBrowseAudioBooks()

    }.stateIn(
        viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = _state.value
    )

    fun onAction(action: AudioBookHomeAction) {
        when (action) {
            is AudioBookHomeAction.OnSearchQueryChange -> {
                _state.update {
                    it.copy(searchQuery = action.query)
                }
            }

            is AudioBookHomeAction.ActivateSearchMode -> {
                _state.update {
                    it.copy(isSearchActive = true)
                }
            }

            is AudioBookHomeAction.DeactivateSearchMode -> {
                _state.update {
                    it.copy(isSearchActive = false)
                }
            }

            is AudioBookHomeAction.OnGenreSelect -> {
                _state.update {
                    it.copy(
                        genre = action.genre,
                        browseAudioBooks = emptyList(),
                        page = 0
                    )
                }
                getBrowseAudioBooks()
            }

            is AudioBookHomeAction.OnGetBrowseAudioBooks -> {
                getBrowseAudioBooks()
            }

            else -> Unit
        }
    }

    @OptIn(FlowPreview::class)
    private fun observeSearchQuery() {
        state.map { it.searchQuery }.distinctUntilChanged().debounce(500L).onEach { query ->
            when {
                query.length < SEARCH_TRIGGER_CHAR -> {
                    _state.update {
                        it.copy(
                            searchErrorMsg = null,
                            searchResult = cachedBooks
                        )
                    }
                }

                query.length >= SEARCH_TRIGGER_CHAR -> {
                    searchJob?.cancel()
                    searchJob = searchAudioBooks(query)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun searchAudioBooks(query: String) = viewModelScope.launch {
        _state.update {
            it.copy(
                isSearchLoading = true
            )
        }
        audioBookRepository.searchAudioBooks(query).onSuccess { searchResult ->
            _state.update {
                it.copy(
                    isSearchLoading = false,
                    searchErrorMsg = null,
                    searchResult = searchResult
                )
            }
        }.onError { error ->
            _state.update {
                it.copy(
                    searchResult = emptyList(),
                    isSearchLoading = false,
                    searchErrorMsg = error.toUiText()
                )
            }
        }
    }

    private fun getBrowseAudioBooks() = viewModelScope.launch {
        _state.update {
            it.copy(
                isBrowseLoading = true
            )
        }

        val page = _state.value.page
        val genre = _state.value.genre

        audioBookRepository.getBrowseAudioBooks(genre = genre, page = page)
            .onSuccess { audioBooks ->
                _state.update { state ->
                    val allBooks = state.browseAudioBooks + audioBooks
                    val uniqueBooks = allBooks.distinctBy { it.id }
                    state.copy(
                        isBrowseLoading = false,
                        browseErrorMsg = null,
                        browseAudioBooks = uniqueBooks,
                        isEndReached = audioBooks.isEmpty(),
                        page = state.page + 1
                    )
                }
            }.onError { error ->
                _state.update {
                    it.copy(
                        browseErrorMsg = error.toUiText(),
                        isBrowseLoading = false
                    )
                }
            }
    }

    private fun observeSavedBooks() {
        observeSaveJob?.cancel()
        observeSaveJob = audioBookRepository.getSavedBooks()
            .map { savedBooks ->
                savedBooks.reversed()
            }
            .onEach { savedBooks ->
                _state.update {
                    it.copy(
                        savedBooks = savedBooks
                    )
                }
            }
            .launchIn(viewModelScope)
    }
}
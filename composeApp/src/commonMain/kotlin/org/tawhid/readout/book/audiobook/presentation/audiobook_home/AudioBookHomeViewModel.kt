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
import org.tawhid.readout.book.audiobook.domain.entity.AudioBook
import org.tawhid.readout.book.audiobook.domain.usecase.GetBrowseAudioBooksUseCase
import org.tawhid.readout.book.audiobook.domain.usecase.GetSavedAudioBooksUseCase
import org.tawhid.readout.book.audiobook.domain.usecase.SearchAudioBooksUseCase
import org.tawhid.readout.core.utils.MAX_BOOKS_TO_FETCH
import org.tawhid.readout.core.utils.SEARCH_TRIGGER_CHAR
import org.tawhid.readout.core.utils.onError
import org.tawhid.readout.core.utils.onSuccess
import org.tawhid.readout.core.utils.toUiText

class AudioBookHomeViewModel(
    private val getSavedAudioBooksUseCase: GetSavedAudioBooksUseCase,
    private val searchAudioBooksUseCase: SearchAudioBooksUseCase,
    private val getBrowseAudioBooksUseCase: GetBrowseAudioBooksUseCase
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
            is AudioBookHomeAction.OnShowInfoDialog -> {
                _state.update {
                    it.copy(
                        showDialog = true
                    )
                }
            }

            is AudioBookHomeAction.OnHideInfoDialog -> {
                _state.update {
                    it.copy(
                        showDialog = false
                    )
                }
            }

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
                        offset = 0
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
        searchAudioBooksUseCase(query).onSuccess { searchResult ->
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

        val offset = _state.value.offset
        val limit = MAX_BOOKS_TO_FETCH
        val genre = _state.value.genre

        getBrowseAudioBooksUseCase(genre = genre, offset = offset, limit = limit)
            .onSuccess { audioBooks ->
                _state.update { state ->
                    val allBooks = state.browseAudioBooks + audioBooks
                    val uniqueBooks = allBooks.distinctBy { it.id }
                    state.copy(
                        isBrowseLoading = false,
                        browseErrorMsg = null,
                        browseAudioBooks = uniqueBooks,
                        isEndReached = audioBooks.isEmpty(),
                        offset = state.offset + limit
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
        observeSaveJob = getSavedAudioBooksUseCase().map { savedBooks ->
            savedBooks.sortedByDescending { it.timeStamp }
        }.onEach { savedBooks ->
            _state.update {
                it.copy(
                    savedBooks = savedBooks
                )
            }
        }.launchIn(viewModelScope)
    }
}
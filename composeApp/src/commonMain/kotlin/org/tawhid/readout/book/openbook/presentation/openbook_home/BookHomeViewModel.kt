package org.tawhid.readout.book.openbook.presentation.openbook_home

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
import org.tawhid.readout.book.openbook.domain.entity.Book
import org.tawhid.readout.book.openbook.domain.repository.BookRepository

import org.tawhid.readout.core.utils.onError
import org.tawhid.readout.core.utils.onSuccess
import org.tawhid.readout.core.utils.MAX_BOOKS_TO_FETCH
import org.tawhid.readout.core.utils.toUiText

class BookHomeViewModel(
    private val bookRepository: BookRepository
) : ViewModel() {

    private val cachedBooks = emptyList<Book>()
    private var searchJob: Job? = null
    private var observeSaveJob: Job? = null

    private val _state = MutableStateFlow(BookHomeState())
    val state = _state.onStart {

        if (cachedBooks.isEmpty()) {
            observeSearchQuery()
        }

        observeSavedBooks()
        getBrowseBooks()
    }
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = _state.value
        )

    fun onAction(action: BookHomeAction) {
        when (action) {
            is BookHomeAction.OnShowInfoDialog -> {
                _state.update {
                    it.copy(
                        showDialog = true
                    )
                }
            }

            is BookHomeAction.OnHideInfoDialog -> {
                _state.update {
                    it.copy(
                        showDialog = false
                    )
                }
            }

            is BookHomeAction.OnSearchQueryChange -> {
                _state.update {
                    it.copy(searchQuery = action.query)
                }
            }

            is BookHomeAction.OnSubjectChange -> {
                _state.update {
                    it.copy(
                        browseBooks = emptyList(),
                        subject = action.subject
                    )
                }
                getBrowseBooks()
            }

            is BookHomeAction.ActivateSearchMode -> {
                _state.update {
                    it.copy(isSearchActive = true)
                }
            }

            is BookHomeAction.DeactivateSearchMode -> {
                _state.update {
                    it.copy(isSearchActive = false)
                }
            }

            is BookHomeAction.OnLoadBrowseBooks -> {
                getBrowseBooks()

            }

            else -> Unit
        }
    }

    private fun observeSavedBooks() {
        observeSaveJob?.cancel()
        observeSaveJob = bookRepository.getSavedBooks()
            .onEach { savedBooks ->
                _state.update {
                    it.copy(
                        savedBooks = savedBooks
                    )
                }
            }
            .launchIn(viewModelScope)
    }


    @OptIn(FlowPreview::class)
    private fun observeSearchQuery() {
        state.map { it.searchQuery }
            .distinctUntilChanged()
            .debounce(500L)
            .onEach { query ->
                when {
                    query.isBlank() -> {
                        _state.update {
                            it.copy(
                                errorMsg = null,
                                searchResult = cachedBooks
                            )
                        }
                    }

                    query.length >= 3 -> {
                        searchJob?.cancel()
                        searchJob = searchBooks(query)
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    private fun searchBooks(query: String) = viewModelScope.launch {
        _state.update {
            it.copy(
                isLoading = true
            )
        }
        bookRepository.searchBooksByQuery(query)
            .onSuccess { searchResult ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMsg = null,
                        searchResult = searchResult
                    )
                }
            }
            .onError { error ->
                _state.update {
                    it.copy(
                        searchResult = emptyList(),
                        isLoading = false,
                        errorMsg = error.toUiText()
                    )
                }
            }

    }

    private fun getBrowseBooks() = viewModelScope.launch {

        _state.update {
            it.copy(
                isBrowseLoading = true
            )
        }

        val offset = _state.value.offset
        val limit = MAX_BOOKS_TO_FETCH
        val subject = _state.value.subject

        bookRepository.getBrowseBooks(subject = subject, offset = offset, limit = limit)
            .onSuccess { browseBooks ->
                _state.update { state ->
                    val allBooks = state.browseBooks + browseBooks
                    val uniqueBooks = allBooks.distinctBy { it.id }
                    state.copy(
                        isBrowseLoading = false,
                        isBrowseShimmerEffectVisible = false,
                        browseErrorMsg = null,
                        browseBooks = uniqueBooks,
                        offset = state.offset + limit
                    )

                }
            }.onError { error ->
            _state.update {
                it.copy(
                    isBrowseLoading = false,
                    isBrowseShimmerEffectVisible = false,
                    browseErrorMsg = error.toUiText()
                )
            }
        }
    }
}
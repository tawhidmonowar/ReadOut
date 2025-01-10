package org.tawhid.readout.book.openbook.presentation.openbook_detail

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
import org.tawhid.readout.book.openbook.domain.Book
import org.tawhid.readout.book.openbook.domain.BookRepository
import org.tawhid.readout.core.domain.onError
import org.tawhid.readout.core.domain.onSuccess
import org.tawhid.readout.core.gemini.GeminiApiPrompts.geminiBookSummaryPrompt
import org.tawhid.readout.core.player.presentation.PlayerAction
import org.tawhid.readout.core.player.presentation.PlayerViewModel
import org.tawhid.readout.core.utils.toUiText

class BookDetailViewModel(
    private val bookRepository: BookRepository,
    private val playerViewModel: PlayerViewModel,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val bookId = savedStateHandle.toRoute<Route.OpenLibraryDetail>().id
    private val _state = MutableStateFlow(BookDetailState())

    val state = _state.onStart {
        fetchBookDescription()
        _state.value.book?.let { insertBookIntoDB(it) }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        _state.value
    )

    fun onAction(action: BookDetailAction) {
        when (action) {
            is BookDetailAction.OnSelectedBookChange -> {
                _state.update {
                    it.copy(
                        book = action.book
                    )
                }
            }

            is BookDetailAction.OnSummaryPlayClick -> {
                getBookSummaryAudio()
            }

            is BookDetailAction.OnSummaryClick -> {
                getBookSummary()
            }

            is BookDetailAction.OnSaveClick -> {
                viewModelScope.launch {
                    state.value.book?.let { book ->
                        bookRepository.saveBook(book)
                    }
                }
            }

            else -> Unit
        }
    }

    private fun fetchBookDescription() {
        viewModelScope.launch {
            if (bookId != null) {
                bookRepository.getBookDescriptionById(bookId).onSuccess { description ->
                    _state.update {
                        it.copy(
                            book = it.book?.copy(
                                description = description
                            ),
                            isLoading = false
                        )
                    }
                }
            }
        }
    }

    private fun getBookSummary() = viewModelScope.launch {
        _state.update {
            it.copy(
                isSummaryLoading = true
            )
        }
        _state.value.book?.let { book ->
            bookRepository.getBookSummary(prompt = geminiBookSummaryPrompt(book))
                .onSuccess { summary ->

                    if (summary != null) {
                        bookRepository.getSummaryAudio(summary = summary)
                            .onSuccess { summaryAudioByteArray ->
                                _state.update {
                                    it.copy(
                                        summaryAudioByteArray = summaryAudioByteArray
                                    )
                                }
                            }
                    }

                    _state.update {
                        it.copy(
                            summary = summary,
                            isSummaryLoading = false
                        )
                    }
                }.onError { error ->
                    _state.update {
                        it.copy(
                            isSummaryLoading = false,
                            summaryErrorMsg = error.toUiText()
                        )
                    }
                }
        }
    }

    private fun getBookSummaryAudio() = viewModelScope.launch {
        _state.update {
            it.copy(
                isSummaryAudioByteArrayLoading = true
            )
        }
        _state.value.book?.let { book ->
            bookRepository.getSummaryAudio(summary = "summary")
                .onSuccess { summaryAudioByteArray ->
                    summaryAudioByteArray?.let {
                        playerViewModel.onAction(PlayerAction.OnPlayAudioBase64Click(it))
                    }
                    _state.update {
                        it.copy(
                            summaryAudioByteArray = summaryAudioByteArray,
                            isSummaryAudioByteArrayLoading = false
                        )
                    }
                }.onError { error ->
                    _state.update {
                        it.copy(
                            isSummaryAudioByteArrayLoading = false,
                            summaryAudioByteArrayErrorMsg = error.toUiText()
                        )
                    }
                }
        }
    }


    private fun insertBookIntoDB(book: Book) {
        viewModelScope.launch {
            bookRepository.saveBook(book)
        }
    }
}
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
import org.tawhid.readout.book.openbook.domain.usecase.DeleteBookFromSavedUseCase
import org.tawhid.readout.book.openbook.domain.usecase.GetBookDescriptionUseCase
import org.tawhid.readout.book.openbook.domain.usecase.GetOpenBookByIdUseCase
import org.tawhid.readout.book.openbook.domain.usecase.GetOpenBookSummaryAudioUseCase
import org.tawhid.readout.book.openbook.domain.usecase.GetOpenBookSummaryUseCase
import org.tawhid.readout.book.openbook.domain.usecase.SaveBookUseCase
import org.tawhid.readout.core.gemini.GeminiApiPrompts.geminiBookSummaryPrompt
import org.tawhid.readout.core.gemini.GeminiApiPrompts.geminiBookSummaryShortPrompt
import org.tawhid.readout.core.player.presentation.PlayerAction
import org.tawhid.readout.core.player.presentation.PlayerViewModel
import org.tawhid.readout.core.utils.onError
import org.tawhid.readout.core.utils.onSuccess
import org.tawhid.readout.core.utils.toUiText

class BookDetailViewModel(
    private val getOpenBookByIdUseCase: GetOpenBookByIdUseCase,
    private val saveBookUseCase: SaveBookUseCase,
    private val deleteBookFromSavedUseCase: DeleteBookFromSavedUseCase,
    private val getBookDescriptionUseCase: GetBookDescriptionUseCase,
    private val getOpenBookSummaryUseCase: GetOpenBookSummaryUseCase,
    private val getOpenBookSummaryAudioUseCase: GetOpenBookSummaryAudioUseCase,
    private val playerViewModel: PlayerViewModel,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val bookId = savedStateHandle.toRoute<Route.OpenLibraryDetail>().id
    private val _state = MutableStateFlow(BookDetailState())

    val state = _state.onStart {
        observeSavedStatus()
        fetchBookDescription()

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
                playBookSummaryAudio()
            }

            is BookDetailAction.OnSummaryClick -> {
                _state.update {
                    it.copy(
                        isSummaryRequest = true,
                    )
                }
                getBookSummary()
            }

            is BookDetailAction.OnSaveClick -> {
                if (state.value.isSaved) {
                    deleteFromSaved()
                } else {
                    saveBook()
                }
            }

            else -> Unit
        }
    }

    private fun fetchBookDescription() {
        viewModelScope.launch {
            if (bookId != null) {
                getBookDescriptionUseCase(bookId).onSuccess { description ->
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
            getOpenBookSummaryUseCase(prompt = geminiBookSummaryPrompt(book), bookId = book.id)
                .onSuccess { summary ->
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


    private fun playBookSummaryAudio() = viewModelScope.launch {
        _state.update {
            it.copy(
                isSummaryAudioLoading = true
            )
        }

        _state.value.book?.let { book ->
            getOpenBookSummaryUseCase(prompt = geminiBookSummaryShortPrompt(book), bookId = "")
                .onSuccess { summary ->
                    _state.update {
                        it.copy(
                            summaryShort = summary
                        )
                    }
                    summary?.let {
                        getOpenBookSummaryAudioUseCase(
                            summary = summary,
                            bookId = book.id
                        ).onSuccess { summaryAudioByteArray ->
                            playerViewModel.onAction(
                                PlayerAction.OnPlayAudioBase64Click(
                                    audioBase64 = summaryAudioByteArray,
                                    nowPlaying = _state.value.book?.title ?: "Unknown"
                                )
                            )
                            _state.update { state ->
                                state.copy(
                                    summaryAudio = summaryAudioByteArray,
                                    isSummaryAudioLoading = false
                                )
                            }
                        }.onError { error ->
                            _state.update { state ->
                                state.copy(
                                    isSummaryAudioLoading = false,
                                    summaryAudioErrorMsg = error.toUiText()
                                )
                            }
                        }
                    }
                }.onError { error ->
                    _state.update {
                        it.copy(
                            isSummaryAudioLoading = false,
                            summaryAudioErrorMsg = error.toUiText()
                        )
                    }
                }
        }
    }

    private fun observeSavedStatus() = viewModelScope.launch {
        bookId?.let { bookId ->
            getOpenBookByIdUseCase(bookId).collect { (book, isSaved) ->
                if (book != null) {
                    _state.update {
                        it.copy(
                            isSaved = isSaved,
                            book = book
                        )
                    }
                } else {
                    _state.update {
                        it.copy(
                            isSaved = isSaved,
                        )
                    }
                }
            }
        }
    }

    private fun saveBook() = viewModelScope.launch {
        state.value.book?.let { book ->
            saveBookUseCase(book)
        }
    }

    private fun deleteFromSaved() = viewModelScope.launch {
        bookId?.let { bookId ->
            deleteBookFromSavedUseCase(bookId)
        }
    }
}
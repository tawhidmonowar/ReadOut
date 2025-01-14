package org.tawhid.readout.book.openbook.presentation.openbook_saved

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
import org.tawhid.readout.book.openbook.domain.repository.BookRepository
import org.tawhid.readout.book.openbook.domain.usecase.GetSaveBooksUseCase

class BookSavedViewModel(
    private val getSaveBooksUseCase: GetSaveBooksUseCase
) : ViewModel() {
    private var observeSaveJob: Job? = null
    private val _state = MutableStateFlow(BookSavedState())

    val state = _state.onStart {
        observeSavedBooks()
    }.stateIn(
        viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = _state.value
    )

    private fun observeSavedBooks() {
        observeSaveJob?.cancel()
        observeSaveJob = getSaveBooksUseCase().onEach { savedBooks ->
            _state.update {
                it.copy(
                    savedBooks = savedBooks
                )
            }
        }.launchIn(viewModelScope)
    }
}
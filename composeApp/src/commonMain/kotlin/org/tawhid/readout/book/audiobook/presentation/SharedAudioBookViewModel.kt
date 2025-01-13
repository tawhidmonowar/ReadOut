package org.tawhid.readout.book.audiobook.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.tawhid.readout.book.audiobook.domain.entity.AudioBook

class SharedAudioBookViewModel : ViewModel() {

    private val _selectedBook = MutableStateFlow<AudioBook?>(null)
    val selectedBook = _selectedBook.asStateFlow()

    fun onSelectBook(book: AudioBook?) {
        _selectedBook.value = book
    }
}
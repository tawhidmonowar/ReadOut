package org.tawhid.readout.book.summarize.presentation.summarize_home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.tawhid.readout.book.summarize.domain.entity.Summarize
import org.tawhid.readout.book.summarize.domain.usecase.GetBookSummaryUseCase
import org.tawhid.readout.core.utils.onError
import org.tawhid.readout.core.utils.onSuccess
import org.tawhid.readout.core.gemini.GeminiApiPrompts.geminiBookSummaryPrompt
import org.tawhid.readout.core.utils.toUiText

class SummarizeViewModel(
    private val getBookSummaryUseCase: GetBookSummaryUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(SummarizeState())
    val state = _state.onStart {}.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        _state.value
    )

    fun onAction(action: SummarizeAction) {
        when (action) {
            is SummarizeAction.OnSummarizeClick -> {
                _state.update {
                    it.copy(
                        summarize = Summarize(
                            title = action.title,
                            authors = action.authors,
                            description = action.description
                        )
                    )
                }
                getBookSummary()
            }

            else -> Unit
        }
    }

    private fun getBookSummary() = viewModelScope.launch {
        _state.update {
            it.copy(
                isSummaryLoading = true,
                isSummaryRequest = true,
            )
        }
        _state.value.summarize?.let { summarize ->
            val prompt = geminiBookSummaryPrompt(summarize)
            getBookSummaryUseCase(prompt).onSuccess { summary ->
                _state.update {
                    it.copy(
                        summarize = it.summarize?.copy(summary = summary),
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
}
package org.tawhid.readout.app.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class HomeViewModel : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

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

}
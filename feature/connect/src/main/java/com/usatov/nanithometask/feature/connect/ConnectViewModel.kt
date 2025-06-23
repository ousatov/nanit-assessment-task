package com.usatov.nanithometask.feature.connect

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.usatov.nanithometask.core.di.IoDispatcher
import com.usatov.nanithometask.domain.connect.StartConnectUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConnectViewModel @Inject constructor(
    private val startUseCase: StartConnectUseCase,
    @IoDispatcher private val io: CoroutineDispatcher
) : ViewModel() {

    private val _state = MutableStateFlow(ConnectUiState())
    val state: StateFlow<ConnectUiState> = _state

    fun onEvent(e: ConnectEvent) = when (e) {
        is ConnectEvent.IpChanged -> _state.update { it.copy(ip = e.value) }
        is ConnectEvent.PortChanged -> _state.update { it.copy(port = e.value) }
        ConnectEvent.ClickConnect -> connect()
    }

    private fun connect() = viewModelScope.launch(io) {
    }
}
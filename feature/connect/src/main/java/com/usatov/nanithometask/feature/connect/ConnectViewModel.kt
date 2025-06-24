package com.usatov.nanithometask.feature.connect

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.usatov.nanithometask.core.common.TAG
import com.usatov.nanithometask.core.common.formatIp
import com.usatov.nanithometask.core.common.formatPort
import com.usatov.nanithometask.core.common.isValidIp
import com.usatov.nanithometask.core.common.logging.Logger
import com.usatov.nanithometask.core.common.resources.ResourceProvider
import com.usatov.nanithometask.core.di.IoDispatcher
import com.usatov.nanithometask.domain.connect.ConnectUseCase
import com.usatov.nanithometask.domain.connect.DisconnectUseCase
import com.usatov.nanithometask.domain.connect.SessionState
import com.usatov.nanithometask.domain.connect.SubscribeConnectStateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import javax.inject.Inject

@HiltViewModel
class ConnectViewModel @Inject constructor(
    private val connectUseCase: ConnectUseCase,
    private val disconnectUseCase: DisconnectUseCase,
    private val subscribeUseCase: SubscribeConnectStateUseCase,
    private val resourceProvider: ResourceProvider,
    private val logger: Logger,
    @IoDispatcher private val io: CoroutineDispatcher
) : ViewModel() {

    private val _state = MutableStateFlow(ConnectUiState())
    val state: StateFlow<ConnectUiState> = _state.asStateFlow()

    private val _navigation = MutableSharedFlow<NavCmd>(
        replay = 0,
        extraBufferCapacity = 1
    )
    val navigation: SharedFlow<NavCmd> = _navigation

    init {
        subscribeUseCase()
            .onEach(::applySessionState)
            .catch { e -> emitError(e.message) }
            .launchIn(viewModelScope + io)
    }

    fun onEvent(event: ConnectEvent) {
        logger.d(TAG, "event = $event")
        when (event) {
            is ConnectEvent.IpChanged -> {
                viewModelScope.launch(io) {
                    disconnectUseCase()
                    val formatted = event.value.formatIp(_state.value.ip)
                    _state.update {
                        it.copy(
                            ip = formatted,
                            errorMessage = null,
                            status = ConnectUiState.Status.Idle
                        )
                    }
                }
            }

            is ConnectEvent.PortChanged -> {
                viewModelScope.launch(io) {
                    disconnectUseCase()
                    val formatted = event.value.formatPort(_state.value.port)
                    _state.update {
                        it.copy(
                            port = formatted,
                            errorMessage = null,
                            status = ConnectUiState.Status.Idle
                        )
                    }
                }
            }

            is ConnectEvent.ClickConnect -> {
                val currentIp = _state.value.ip
                val currentPort = _state.value.port

                if (!currentIp.isValidIp()) {
                    _state.update {
                        it.copy(
                            status = ConnectUiState.Status.Error,
                            errorMessage = resourceProvider.getString(R.string.incorrect_format_ip)
                        )
                    }
                    return
                }

                _state.update {
                    it.copy(status = ConnectUiState.Status.Connecting, errorMessage = null)
                }
                viewModelScope.launch(io) {
                    try {
                        disconnectUseCase()
                        connectUseCase(currentIp, currentPort.toInt())
                    } catch (e: Exception) {
                        _state.update {
                            it.copy(
                                status = ConnectUiState.Status.Error,
                                errorMessage = e.message
                                    ?: resourceProvider.getString(R.string.unknown_error)
                            )
                        }
                    }
                }
            }

            ConnectEvent.ClickDone -> {
                tryNavigateToOverview()
            }
        }
    }

    private fun applySessionState(state: SessionState) {
        logger.d(TAG, "applySessionState() state = $state")
        when (state) {
            SessionState.Connecting ->
                _state.update { it.copy(status = ConnectUiState.Status.Connecting) }

            SessionState.Connected ->
                _state.update { it.copy(status = ConnectUiState.Status.Connected) }

            is SessionState.Error ->
                emitError(
                    state.throwable.message ?: resourceProvider.getString(R.string.unknown_error)
                )

            is SessionState.Disconnected -> {
                _state.update { it.copy(status = ConnectUiState.Status.Idle) }
            }
        }
    }

    private fun emitError(msg: String?) {
        logger.d(TAG, "emitError() = $msg")
        _state.update {
            it.copy(
                status = ConnectUiState.Status.Error,
                errorMessage = msg
            )
        }
    }

    private fun tryNavigateToOverview() {
        if (state.value.status == ConnectUiState.Status.Connected) {
            _navigation.tryEmit(NavCmd.ToBirthday)
        }
    }

    override fun onCleared() {
        logger.d(TAG, "onCleared()")
//        viewModelScope.launch(io) {
//            disconnectUseCase()
//        }
    }
}
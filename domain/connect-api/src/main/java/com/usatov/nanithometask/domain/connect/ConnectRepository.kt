package com.usatov.nanithometask.domain.connect

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

sealed class SessionState {
    data object Connecting : SessionState()
    data object Connected : SessionState()
    data object Disconnected : SessionState()
    data class Error(val throwable: Throwable) : SessionState()
}

interface ConnectRepository {
    val state: StateFlow<SessionState>
    suspend fun connect(ip: String, port: Int)
    suspend fun disconnect()
}
package com.usatov.nanithometask.data.connect

import kotlinx.coroutines.flow.Flow


sealed class SocketState {
    data class Data(val payload: String) : SocketState()
    data object Connected : SocketState()
    data object Disconnected : SocketState()
    data class Error(val throwable: Throwable) : SocketState()
}

interface SocketDataSource {
    fun subscribe(ip: String, port: Int): Flow<SocketState>
}
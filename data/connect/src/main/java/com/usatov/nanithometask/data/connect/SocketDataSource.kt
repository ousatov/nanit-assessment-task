package com.usatov.nanithometask.data.connect

import com.usatov.nanithometask.domain.connect.SessionState
import kotlinx.coroutines.flow.Flow

interface SocketDataSource {
    fun connect(pass: String): Flow<SessionState>
}
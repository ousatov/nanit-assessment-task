package com.usatov.nanithometask.data.connect

import com.usatov.nanithometask.domain.connect.SessionState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FakeSocketDataSource @Inject constructor() : SocketDataSource {
    override fun connect(pass: String): Flow<SessionState> = flow {
        emit(SessionState.Connecting)
        delay(1_000)
        emit(SessionState.Connected)
    }
}

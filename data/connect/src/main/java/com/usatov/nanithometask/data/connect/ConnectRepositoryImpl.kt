package com.usatov.nanithometask.data.connect

import com.usatov.nanithometask.domain.connect.ConnectRepository
import com.usatov.nanithometask.domain.connect.SessionState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ConnectRepositoryImpl @Inject constructor(
    private val socket: SocketDataSource,
) : ConnectRepository {
    override fun connect(password: String): Flow<SessionState> =
        socket.connect(password)
}
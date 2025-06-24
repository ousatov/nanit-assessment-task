package com.usatov.nanithometask.data.connect

import android.util.Log
import com.usatov.nanithometask.core.db.birthday.LocalBirthdayDataSource
import com.usatov.nanithometask.core.di.IoDispatcher
import com.usatov.nanithometask.domain.connect.ConnectRepository
import com.usatov.nanithometask.domain.connect.SessionState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.withIndex
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConnectRepositoryImpl @Inject constructor(
    private val socket: SocketDataSource,
    private val local: LocalBirthdayDataSource,
    private val json: Json,
    @IoDispatcher private val io: CoroutineDispatcher
) : ConnectRepository {

    private val _state = MutableStateFlow<SessionState>(SessionState.Disconnected)
    override val state: StateFlow<SessionState> = _state

    private val repoScope = CoroutineScope(SupervisorJob() + io)
    private var socketJob: Job? = null

    override suspend fun connect(ip: String, port: Int) = withContext(io) {
        if (socketJob?.isActive == true) {
            return@withContext
        }

        _state.value = SessionState.Connecting
        socketJob = repoScope.launch {
            runCatching {
                socket.subscribe(ip, port)
                    .withIndex()
                    .collect { (idx, s) -> handleSocketEvent(idx, s) }
            }.onFailure { e ->
                _state.value = SessionState.Error(e)
            }
        }.also { job -> job.invokeOnCompletion { socketJob = null } }
    }

    override suspend fun disconnect() = withContext(io) {
        socketJob?.cancelAndJoin()
        socketJob = null
        _state.value = SessionState.Disconnected
    }

    private suspend fun handleSocketEvent(idx: Int, s: SocketState) {
        Log.d("REPOSITORY", "state = $s")
        when (s) {
            is SocketState.Connected -> _state.value = SessionState.Connected
            is SocketState.Disconnected -> _state.value = SessionState.Disconnected
            is SocketState.Error -> _state.value = SessionState.Error(s.throwable)
            is SocketState.Data -> handlePayload(idx, s.payload)
        }
    }

    private suspend fun handlePayload(idx: Int, raw: String) {
        if (idx == 0 && raw.startsWith("HTTP/")) {
            val code = raw.split(' ').getOrNull(1)?.toIntOrNull()
            if (code == null || code !in 200..299) {
                _state.value = SessionState.Error(IOException("HTTP $code: $raw"))
                disconnect()
            } else {
                _state.value = SessionState.Connected
            }
            return
        }

        Log.d("US_11", "raw = $raw")
        runCatching { json.decodeFromString<BirthdayDto>(raw) }
            .onFailure { Log.e("US_11", "decode error", it) }
            .getOrNull()
            ?.let { dto ->
                Log.d("US_11", "saved = ${dto.toEntity()}")
                local.save(dto.toEntity())
                local.deleteOlderExceptLast(KEEP)
            }
    }

    private companion object {
        const val KEEP = 10
    }
}
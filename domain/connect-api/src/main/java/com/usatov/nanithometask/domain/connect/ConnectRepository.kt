package com.usatov.nanithometask.domain.connect

import kotlinx.coroutines.flow.Flow

enum class SessionState { Idle, Connecting, Connected, Error }

interface ConnectRepository {
    fun connect(password: String): Flow<SessionState>
}
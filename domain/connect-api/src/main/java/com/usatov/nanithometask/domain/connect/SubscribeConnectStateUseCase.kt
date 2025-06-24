package com.usatov.nanithometask.domain.connect

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SubscribeConnectStateUseCase @Inject constructor(
    private val repo: ConnectRepository
) {
    operator fun invoke(): Flow<SessionState> = repo.state
}
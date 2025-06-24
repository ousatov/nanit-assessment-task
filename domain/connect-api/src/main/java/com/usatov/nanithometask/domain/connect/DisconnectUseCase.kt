package com.usatov.nanithometask.domain.connect

import javax.inject.Inject

class DisconnectUseCase @Inject constructor(
    private val repo: ConnectRepository
) {
    suspend operator fun invoke() {
        repo.disconnect()
    }
}
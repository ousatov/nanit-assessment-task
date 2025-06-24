package com.usatov.nanithometask.domain.connect

import javax.inject.Inject

class ConnectUseCase @Inject constructor(
    private val repo: ConnectRepository
) {
    suspend operator fun invoke(ip: String, port: Int) {
        repo.connect(ip, port)
    }
}
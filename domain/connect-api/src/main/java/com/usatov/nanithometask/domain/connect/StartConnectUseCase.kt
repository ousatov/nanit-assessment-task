package com.usatov.nanithometask.domain.connect

import javax.inject.Inject

class StartConnectUseCase @Inject constructor(private val repo: ConnectRepository) {
    operator fun invoke(pass: String) = repo.connect(pass)
}
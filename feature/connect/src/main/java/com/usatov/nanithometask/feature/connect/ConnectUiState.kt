package com.usatov.nanithometask.feature.connect

data class ConnectUiState(
    val ip: String = "192.168.0.147",
    val port: String = "8080",
    val status: Status = Status.Idle,
    val errorMessage: String? = null
) {
    enum class Status { Idle, Connecting, Connected, Error }
}
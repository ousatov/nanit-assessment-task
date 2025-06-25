package com.usatov.nanithometask.feature.connect

data class ConnectUiState(
    val ip: String = "",
    val port: String = "",
    val status: Status = Status.Idle,
    val errorMessage: String? = null
) {
    enum class Status { Idle, Connecting, Connected, Error }
}
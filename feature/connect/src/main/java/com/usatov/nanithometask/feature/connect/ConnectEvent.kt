package com.usatov.nanithometask.feature.connect

sealed class ConnectEvent {
    data class IpChanged(val value: String) : ConnectEvent()
    data class PortChanged(val value: String) : ConnectEvent()
    data object ClickConnect : ConnectEvent()
    data object ClickDone : ConnectEvent()
}
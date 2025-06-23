package com.usatov.nanithometask.feature.connect

sealed interface ConnectEvent {
    data class IpChanged(val value: String) : ConnectEvent
    data class PortChanged(val value: String) : ConnectEvent
    data object ClickConnect : ConnectEvent
}
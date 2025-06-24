package com.usatov.nanithometask.feature.connect

sealed interface NavCmd {
    data object ToBirthday : NavCmd
    // other possible routes
}
package com.usatov.nanithometask.core.common

class NanitConstants {
    companion object {
        val IP_PARTIAL_REGEX = Regex("""^(\d{1,3}(\.\d{0,3}){0,3})?$""")
        val IP_REGEX = Regex(
            """^(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3}$"""
        )
        const val MAX_PORT_LENGTH = 5
        const val MAX_PORT_VALUE = 65535
        const val MIN_PORT_VALUE = 1
    }
}
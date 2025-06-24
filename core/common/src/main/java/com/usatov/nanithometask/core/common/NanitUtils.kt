package com.usatov.nanithometask.core.common

import com.usatov.nanithometask.core.common.NanitConstants.Companion.IP_PARTIAL_REGEX
import com.usatov.nanithometask.core.common.NanitConstants.Companion.IP_REGEX
import com.usatov.nanithometask.core.common.NanitConstants.Companion.MAX_PORT_LENGTH
import com.usatov.nanithometask.core.common.NanitConstants.Companion.MAX_PORT_VALUE
import com.usatov.nanithometask.core.common.NanitConstants.Companion.MIN_PORT_VALUE


fun String.formatIp(current: String): String {
    val raw = this
    var input = raw

    if (current.endsWith('.') && current.length - input.length == 1) {
        input = input.dropLast(1)
    }

    val sb = StringBuilder()
    var segLen = 0
    var dotCount = 0

    input.forEach { ch ->
        when {
            ch.isDigit() && segLen < 3 -> {
                sb.append(ch); segLen++
            }

            ch == '.' && segLen > 0 && dotCount < 3 -> {
                sb.append('.'); dotCount++; segLen = 0
            }
        }
    }

    if (segLen == 3 && dotCount < 3) {
        sb.append('.')
    }

    val formatted = sb.toString()
    return if (IP_PARTIAL_REGEX.matches(formatted)) {
        formatted
    } else {
        current
    }
}

fun String.formatPort(current: String): String {
    val digits = this.filter { it.isDigit() }
    val limited = digits.take(MAX_PORT_LENGTH)

    val accept = when {
        limited.isEmpty() -> true
        limited.length < MAX_PORT_LENGTH -> true
        else -> {
            val num = limited.toIntOrNull() ?: return limited
            num in MIN_PORT_VALUE..MAX_PORT_VALUE
        }
    }

    return if (accept) {
        limited
    } else {
        current
    }
}

fun String.isValidIp() = IP_REGEX.matches(this)

inline val <reified T> T.TAG: String
    get() = T::class.simpleName?.take(23) ?: "Anonymous"
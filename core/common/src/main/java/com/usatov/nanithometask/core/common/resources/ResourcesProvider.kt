package com.usatov.nanithometask.core.common.resources

import androidx.annotation.PluralsRes
import androidx.annotation.StringRes

interface ResourceProvider {
    fun getString(@StringRes id: Int): String

    fun getQuantityString(@PluralsRes id: Int, quantity: Int, vararg args: Any): String
}
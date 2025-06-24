package com.usatov.nanithometask.core.common.resources

import androidx.annotation.StringRes

interface ResourceProvider {
    fun getString(@StringRes id: Int): String
}
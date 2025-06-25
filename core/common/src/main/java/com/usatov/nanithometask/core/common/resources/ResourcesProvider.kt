package com.usatov.nanithometask.core.common.resources

import androidx.annotation.ArrayRes
import androidx.annotation.DrawableRes
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes

interface ResourceProvider {
    fun getString(@StringRes id: Int): String
    fun getString(@StringRes id: Int, vararg formatArgs: Any): String

    fun getQuantityString(@PluralsRes id: Int, quantity: Int, vararg args: Any): String

    @DrawableRes
    fun getDrawableFromArray(@ArrayRes arrayResId: Int, index: Int): Int
}
package com.usatov.nanithometask.core.common.resources

import android.content.Context
import androidx.annotation.PluralsRes

// SIMPLIFIED. SHOULD BE IN ANOTHER MODULE
class AndroidResourceProvider(private val context: Context) : ResourceProvider {
    override fun getString(id: Int): String =
        context.getString(id)

    override fun getQuantityString(
        @PluralsRes id: Int,
        quantity: Int,
        vararg args: Any
    ): String = context.resources.getQuantityString(id, quantity, *args)
}
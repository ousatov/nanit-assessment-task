package com.usatov.nanithometask.core.common.resources

import android.content.Context

// SIMPLIFIED. SHOULD BE IN ANOTHER MODULE
class AndroidResourceProvider(private val context: Context) : ResourceProvider {
    override fun getString(id: Int): String =
        context.getString(id)
}
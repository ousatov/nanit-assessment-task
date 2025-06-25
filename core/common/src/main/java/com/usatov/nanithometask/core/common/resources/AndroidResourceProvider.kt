package com.usatov.nanithometask.core.common.resources

import android.content.Context
import android.util.SparseArray
import androidx.annotation.ArrayRes
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes

// SIMPLIFIED. SHOULD BE IN ANOTHER MODULE
class AndroidResourceProvider(private val context: Context) : ResourceProvider {

    private val arrayCache = SparseArray<IntArray>()

    override fun getString(@StringRes id: Int): String =
        context.getString(id)

    override fun getString(@StringRes id: Int, vararg formatArgs: Any): String =
        context.getString(id, *formatArgs)

    override fun getQuantityString(
        @PluralsRes id: Int,
        quantity: Int,
        vararg args: Any
    ): String = context.resources.getQuantityString(id, quantity, *args)

    override fun getDrawableFromArray(@ArrayRes arrayResId: Int, index: Int): Int {
        val icons = arrayCache[arrayResId] ?: run {
            val ta = context.resources.obtainTypedArray(arrayResId)
            val list = IntArray(ta.length()) { ta.getResourceId(it, 0) }
            ta.recycle()
            arrayCache.put(arrayResId, list)
            list
        }
        val safe = index.coerceIn(0, icons.lastIndex)
        return icons[safe]
    }
}
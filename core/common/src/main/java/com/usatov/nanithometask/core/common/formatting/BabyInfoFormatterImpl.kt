package com.usatov.nanithometask.core.common.formatting

import androidx.annotation.ArrayRes
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes
import com.usatov.nanithometask.core.common.resources.ResourceProvider
import java.util.Calendar

class BabyInfoFormatterImpl(
    private val resourceProvider: ResourceProvider
) : BabyInfoFormatter {

    override fun getAgeIcon(
        dobMillis: Long,
        @ArrayRes digitIconsArrayRes: Int
    ): Int = resourceProvider.getDrawableFromArray(
        digitIconsArrayRes,
        dobMillis.toAgeDigit()
    )

    override fun formatName(
        name: String,
        @StringRes todayNameTemplateRes: Int
    ): String = resourceProvider.getString(
        todayNameTemplateRes,
        name
    )

    override fun formatAge(
        dobMillis: Long,
        @PluralsRes ageMonthsPluralRes: Int,
        @PluralsRes ageYearsPluralRes: Int
    ): String {
        val totalMonths = dobMillis.monthsSince()
        return if (totalMonths < MONTHS_IN_YEAR) {
            resourceProvider.getQuantityString(
                ageMonthsPluralRes,
                totalMonths,
                totalMonths
            )
        } else {
            val years = (totalMonths / MONTHS_IN_YEAR).coerceAtMost(MAX_YEARS)
            resourceProvider.getQuantityString(
                ageYearsPluralRes,
                years,
                years
            )
        }
    }

    private fun Long.toAgeDigit(): Int {
        val months = monthsSince()
        return if (months < MONTHS_IN_YEAR) {
            months.coerceAtLeast(0)
        } else {
            (months / MONTHS_IN_YEAR).coerceAtMost(MAX_YEARS)
        }
    }

    private fun Long.monthsSince(): Int {
        val then = Calendar.getInstance().apply { timeInMillis = this@monthsSince }
        val now = Calendar.getInstance()

        return ((now.get(Calendar.YEAR) - then.get(Calendar.YEAR)) * MONTHS_IN_YEAR +
                (now.get(Calendar.MONTH) - then.get(Calendar.MONTH)))
            .coerceAtLeast(0)
    }

    companion object {
        const val MONTHS_IN_YEAR = 12
        const val MAX_YEARS = 9
    }
}
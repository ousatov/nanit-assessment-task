package com.usatov.nanithometask.core.common.formatting

import androidx.annotation.ArrayRes
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes

interface BabyInfoFormatter {

    fun getAgeIcon(
        dobMillis: Long,
        @ArrayRes digitIconsArrayRes: Int
    ): Int

    fun formatName(
        name: String,
        @StringRes todayNameTemplateRes: Int
    ): String

    fun formatAge(
        dobMillis: Long,
        @PluralsRes ageMonthsPluralRes: Int,
        @PluralsRes ageYearsPluralRes: Int
    ): String
}
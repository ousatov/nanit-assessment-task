package com.usatov.nanithometask.data.birthday

import com.usatov.nanithometask.core.db.birthday.BirthdayEntity
import com.usatov.nanithometask.domain.birthday.Birthday
import com.usatov.nanithometask.domain.birthday.BirthdayTheme

internal fun BirthdayEntity.toDomain() = Birthday(
    name = name,
    dob = dob,
    theme = theme.toBirthdayTheme()
)

private fun String.toBirthdayTheme(): BirthdayTheme =
    when (lowercase()) {
        "elephant" -> BirthdayTheme.ELEPHANT
        "fox" -> BirthdayTheme.FOX
        "pelican" -> BirthdayTheme.PELICAN
        else -> BirthdayTheme.ELEPHANT
    }
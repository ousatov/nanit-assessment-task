package com.usatov.nanithometask.feature.birthday

import com.usatov.nanithometask.domain.birthday.BirthdayTheme

data class UiBirthday(
    val name: String,
    val ageLabel: String,
    val theme: BirthdayTheme
)
package com.usatov.nanithometask.feature.birthday

import com.usatov.nanithometask.domain.birthday.BirthdayTheme

data class UiBirthday(
    val nameLabel: String,
    val ageLabel: String,
    val ageResource: Int,
    val theme: BirthdayTheme
)
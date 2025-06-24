package com.usatov.nanithometask.domain.birthday

enum class BirthdayTheme { ELEPHANT, FOX, PELICAN }

data class Birthday(
    val name: String,
    val dob: Long,
    val theme: BirthdayTheme
)
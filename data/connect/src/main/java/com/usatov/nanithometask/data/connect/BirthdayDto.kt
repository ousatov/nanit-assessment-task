package com.usatov.nanithometask.data.connect

import com.usatov.nanithometask.core.db.birthday.BirthdayEntity
import kotlinx.serialization.Serializable

@Serializable
internal data class BirthdayDto(
    val name: String,
    val dob: Long,
    val theme: String
)

internal fun BirthdayDto.toEntity() = BirthdayEntity(
    name = name,
    dob = dob,
    theme = theme
)









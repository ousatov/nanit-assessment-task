package com.usatov.nanithometask.data.birthday

import com.usatov.nanithometask.core.db.birthday.LocalBirthdayDataSource
import com.usatov.nanithometask.domain.birthday.Birthday
import com.usatov.nanithometask.domain.birthday.SubscribeBirthdayUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class SubscribeBirthdayUseCaseImpl @Inject constructor(
    private val local: LocalBirthdayDataSource
) : SubscribeBirthdayUseCase {

    override fun invoke(): Flow<Birthday?> =
        local.observeLast()
            .map { it?.toDomain() }
}
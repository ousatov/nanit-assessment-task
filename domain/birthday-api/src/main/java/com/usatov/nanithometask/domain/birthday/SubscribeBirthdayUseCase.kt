package com.usatov.nanithometask.domain.birthday

import kotlinx.coroutines.flow.Flow

interface SubscribeBirthdayUseCase {
    operator fun invoke(): Flow<Birthday?>
}
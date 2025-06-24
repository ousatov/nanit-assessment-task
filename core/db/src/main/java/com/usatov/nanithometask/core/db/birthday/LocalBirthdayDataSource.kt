package com.usatov.nanithometask.core.db.birthday

import kotlinx.coroutines.flow.Flow

interface LocalBirthdayDataSource {
    suspend fun save(entity: BirthdayEntity)
    fun observeLast(): Flow<BirthdayEntity?>
    suspend fun deleteOlderExceptLast(keep: Int)
}
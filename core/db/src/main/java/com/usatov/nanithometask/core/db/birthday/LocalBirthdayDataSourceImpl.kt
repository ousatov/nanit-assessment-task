package com.usatov.nanithometask.core.db.birthday

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalBirthdayDataSourceImpl @Inject constructor(
    private val dao: BirthdayDao
) : LocalBirthdayDataSource {

    override suspend fun save(entity: BirthdayEntity) {
        dao.insert(entity)
    }

    override fun observeLast(): Flow<BirthdayEntity?> = dao.observeLast()

    override suspend fun deleteOlderExceptLast(keep: Int) {
        dao.deleteOlderExceptLast(keep)
    }
}
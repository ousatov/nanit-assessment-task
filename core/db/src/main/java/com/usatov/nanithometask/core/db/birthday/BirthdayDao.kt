package com.usatov.nanithometask.core.db.birthday

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BirthdayDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: BirthdayEntity)

    @Query(
        """
        SELECT * FROM birthdays
        ORDER BY id DESC
        LIMIT 1
    """
    )
    fun observeLast(): Flow<BirthdayEntity?>

    @Query(
        """
    DELETE FROM birthdays
    WHERE id NOT IN (
        SELECT id FROM birthdays
        ORDER BY id DESC
        LIMIT :keep)
        """
    )
    suspend fun deleteOlderExceptLast(keep: Int = 10)
}
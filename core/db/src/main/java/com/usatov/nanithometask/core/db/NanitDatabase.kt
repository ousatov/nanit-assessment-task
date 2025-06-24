package com.usatov.nanithometask.core.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.usatov.nanithometask.core.db.birthday.BirthdayDao
import com.usatov.nanithometask.core.db.birthday.BirthdayEntity


@Database(
    entities = [BirthdayEntity::class],
    version = 1,
    exportSchema = false
)
abstract class NanitDatabase : RoomDatabase() {

    abstract fun birthdayDao(): BirthdayDao
}
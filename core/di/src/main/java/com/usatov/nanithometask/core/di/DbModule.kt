package com.usatov.nanithometask.core.di

import android.content.Context
import androidx.room.Room
import com.usatov.nanithometask.core.db.NanitDatabase
import com.usatov.nanithometask.core.db.birthday.BirthdayDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DbModule {

    @Provides
    @Singleton
    fun provideNanitDatabase(
        @ApplicationContext ctx: Context
    ): NanitDatabase =
        Room.databaseBuilder(ctx, NanitDatabase::class.java, "nanit_test.db").build()

    @Provides
    fun provideBirthdayDao(db: NanitDatabase): BirthdayDao = db.birthdayDao()
}
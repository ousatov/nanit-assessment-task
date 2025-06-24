package com.usatov.nanithometask.core.di

import com.usatov.nanithometask.core.db.birthday.LocalBirthdayDataSource
import com.usatov.nanithometask.core.db.birthday.LocalBirthdayDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindLocalPersonDataSource(
        impl: LocalBirthdayDataSourceImpl
    ): LocalBirthdayDataSource
}
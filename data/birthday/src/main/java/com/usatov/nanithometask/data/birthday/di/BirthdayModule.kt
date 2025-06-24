package com.usatov.nanithometask.data.birthday.di

import com.usatov.nanithometask.data.birthday.SubscribeBirthdayUseCaseImpl
import com.usatov.nanithometask.domain.birthday.SubscribeBirthdayUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class OverviewModule {

    @Binds
    @Singleton
    abstract fun bindSubscribeBirthdayUseCase(
        impl: SubscribeBirthdayUseCaseImpl
    ): SubscribeBirthdayUseCase
}
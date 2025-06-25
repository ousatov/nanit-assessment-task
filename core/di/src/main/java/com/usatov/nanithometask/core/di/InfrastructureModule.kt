package com.usatov.nanithometask.core.di

import android.content.Context
import com.usatov.nanithometask.core.common.formatting.BabyInfoFormatter
import com.usatov.nanithometask.core.common.formatting.BabyInfoFormatterImpl
import com.usatov.nanithometask.core.common.logging.Logger
import com.usatov.nanithometask.core.common.logging.TimberLogger
import com.usatov.nanithometask.core.common.resources.AndroidResourceProvider
import com.usatov.nanithometask.core.common.resources.ResourceProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object InfrastructureModule {
    @Provides
    @Singleton
    fun provideLogger(): Logger = TimberLogger()

    @Provides
    @Singleton
    fun provideResourceProvider(
        @ApplicationContext context: Context
    ): ResourceProvider = AndroidResourceProvider(context)


    @Provides
    @Singleton
    fun provideBabyInfoFormatter(
        resourceProvider: ResourceProvider
    ): BabyInfoFormatter = BabyInfoFormatterImpl(resourceProvider)
}

// SIMPLIFIED..
@EntryPoint
@InstallIn(SingletonComponent::class)
interface AppInitEntryPoint {
    fun logger(): Logger
}

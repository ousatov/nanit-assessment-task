package com.usatov.nanithometask.data.connect.di

import com.usatov.nanithometask.data.connect.ConnectRepositoryImpl
import com.usatov.nanithometask.data.connect.SocketDataSource
import com.usatov.nanithometask.data.connect.SocketDataSourceImpl
import com.usatov.nanithometask.domain.connect.ConnectRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface ConnectDataModule {

    @Binds
    @Singleton
    fun bindConnectRepository(impl: ConnectRepositoryImpl): ConnectRepository

    @Binds
    fun bindSocketDataSource(ds: SocketDataSourceImpl): SocketDataSource
}
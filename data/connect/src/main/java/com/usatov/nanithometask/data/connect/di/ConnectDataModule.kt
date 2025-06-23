package com.usatov.nanithometask.data.connect.di

import com.usatov.nanithometask.data.connect.ConnectRepositoryImpl
import com.usatov.nanithometask.data.connect.FakeSocketDataSource
import com.usatov.nanithometask.data.connect.SocketDataSource
import com.usatov.nanithometask.domain.connect.ConnectRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ConnectDataModule {

    @Binds
    fun bindRepo(impl: ConnectRepositoryImpl): ConnectRepository

    @Binds
    fun bindSocket(ds: FakeSocketDataSource): SocketDataSource
}
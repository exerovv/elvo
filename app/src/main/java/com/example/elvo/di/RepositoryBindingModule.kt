package com.example.elvo.di

import com.example.elvo.data.network.implementations.AuthRepositoryImpl
import com.example.elvo.data.network.implementations.DataStoreRepositoryImpl
import com.example.elvo.domain.repositories.AuthRepository
import com.example.elvo.domain.repositories.DataStoreRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryBindingModule {
    @Binds
    abstract fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl) : AuthRepository

    @Binds
    abstract fun bindDataStoreRepository(dataStoreRepositoryImpl: DataStoreRepositoryImpl) : DataStoreRepository
}
package com.example.elvo.di

import com.example.elvo.data.network.implementations.AuthRepositoryImpl
import com.example.elvo.data.network.implementations.DataStoreRepositoryImpl
import com.example.elvo.data.network.implementations.FaqRepositoryImpl
import com.example.elvo.data.network.implementations.PopularRepositoryImpl
import com.example.elvo.data.network.implementations.RecipientRepositoryImpl
import com.example.elvo.domain.repositories.AuthRepository
import com.example.elvo.domain.repositories.DataStoreRepository
import com.example.elvo.domain.repositories.FaqRepository
import com.example.elvo.domain.repositories.PopularRepository
import com.example.elvo.domain.repositories.RecipientRepository
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

    @Binds
    abstract fun bindPopularRepository(popularRepositoryImpl: PopularRepositoryImpl): PopularRepository

    @Binds
    abstract fun bindFaqRepository(faqRepositoryImpl: FaqRepositoryImpl): FaqRepository

    @Binds
    abstract fun bindRecipientRepository(recipientRepositoryImpl: RecipientRepositoryImpl): RecipientRepository
}
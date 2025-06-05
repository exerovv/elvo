package com.example.elvo.di

import com.example.elvo.domain.repositories.AuthRepository
import com.example.elvo.domain.repositories.DataStoreRepository
import com.example.elvo.domain.repositories.PopularRepository
import com.example.elvo.domain.usecase.FetchPopularItemsUseCase
import com.example.elvo.domain.usecase.auth.LoginUseCase
import com.example.elvo.domain.usecase.auth.RefreshUseCase
import com.example.elvo.domain.usecase.auth.RegisterUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@InstallIn(SingletonComponent::class)
@Module
object UseCaseModule {
    @Provides
    fun provideLoginUseCase(authRepository: AuthRepository, dataStoreRepository: DataStoreRepository): LoginUseCase {
        return LoginUseCase(authRepository, dataStoreRepository)
    }

    @Provides
    fun provideRegisterUseCase(authRepository: AuthRepository, dataStoreRepository: DataStoreRepository): RegisterUseCase {
        return RegisterUseCase(authRepository, dataStoreRepository)
    }

    @Provides
    fun provideRefreshUseCase(authRepository: AuthRepository, dataStoreRepository: DataStoreRepository): RefreshUseCase {
        return RefreshUseCase(authRepository, dataStoreRepository)
    }

    @Provides
    fun provideFetchPopularItemsUseCase(popularRepository: PopularRepository): FetchPopularItemsUseCase{
        return FetchPopularItemsUseCase(popularRepository)
    }
}
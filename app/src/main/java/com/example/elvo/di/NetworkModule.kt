package com.example.elvo.di

import com.example.elvo.data.network.services.AuthService
import com.example.elvo.data.network.services.PopularService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://192.168.1.10:8080/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthService(retrofit: Retrofit): AuthService{
        return retrofit.create(AuthService::class.java)
    }

    @Provides
    @Singleton
    fun providePopularService(retrofit: Retrofit): PopularService{
        return retrofit.create(PopularService::class.java)
    }
}


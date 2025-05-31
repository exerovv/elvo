package com.example.elvo.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.example.elvo.data.network.services.AuthService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {
    @Provides
    @Singleton
    fun provideRetrofit(): AuthService {
        val retrofit = Retrofit.Builder()
            .baseUrl("")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
        return retrofit.create()
    }
}

@InstallIn(SingletonComponent::class)
@Module
object DataStoreModule {

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context) : DataStore<Preferences>{
        return PreferenceDataStoreFactory.create {
            context.preferencesDataStoreFile("tokens")
        }
    }
}
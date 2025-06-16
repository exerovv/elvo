package com.example.elvo.di

import com.example.elvo.data.network.services.AuthService
import com.example.elvo.data.network.services.FaqService
import com.example.elvo.data.network.services.OrderService
import com.example.elvo.data.network.services.PopularService
import com.example.elvo.data.network.services.RecipientService
import com.example.elvo.data.utils.AuthInterceptor
import com.example.elvo.domain.repositories.DataStoreRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {
    @Provides
    @Singleton
    fun provideInterceptor(dataStoreRepository: DataStoreRepository): Interceptor{
        return AuthInterceptor(dataStoreRepository)
    }

    @Provides
    @Singleton
    @Named("Unauthenticated")
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://elvo-backend-production.up.railway.app/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    @Named("Authenticated")
    fun provideAuthRetrofit(authInterceptor: Interceptor): Retrofit {
        val client = OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .build()
        return Retrofit.Builder()
            .baseUrl("https://elvo-backend-production.up.railway.app/")
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthService(@Named("Unauthenticated") retrofit: Retrofit): AuthService{
        return retrofit.create(AuthService::class.java)
    }

    @Provides
    @Singleton
    fun providePopularService(@Named("Unauthenticated") retrofit: Retrofit): PopularService{
        return retrofit.create(PopularService::class.java)
    }

    @Provides
    @Singleton
    fun provideFaqService(@Named("Unauthenticated") retrofit: Retrofit): FaqService{
        return retrofit.create(FaqService::class.java)
    }

    @Provides
    @Singleton
    fun provideRecipientService(@Named("Authenticated") retrofit: Retrofit): RecipientService{
        return retrofit.create(RecipientService::class.java)
    }

    @Provides
    @Singleton
    fun provideOrderService(@Named("Authenticated") retrofit: Retrofit): OrderService{
        return retrofit.create(OrderService::class.java)
    }

}


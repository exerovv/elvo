package com.example.elvo.di

import android.content.Context
import com.example.elvo.data.database.RoomAppDataSource
import com.example.elvo.data.database.dao.FaqDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideRoomAppDataSource(
        @ApplicationContext context: Context
    ): RoomAppDataSource {
        return RoomAppDataSource.buildDataSource(context)
    }

    @Provides
    fun provideFaqDao(db: RoomAppDataSource): FaqDao = db.faqDao()
}

package com.example.elvo.data.database


import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import androidx.room.Database
import com.example.elvo.contracts.RoomContract
import com.example.elvo.data.database.dao.FaqDao
import com.example.elvo.data.database.models.FaqEntity

@Database(
    entities = [FaqEntity::class],
    version = 1,
    exportSchema = false
)
abstract class RoomAppDataSource : RoomDatabase() {

    abstract fun faqDao(): FaqDao

    companion object {
        fun buildDataSource(context: Context): RoomAppDataSource =
            Room.databaseBuilder(
                context.applicationContext,
                RoomAppDataSource::class.java,
                RoomContract.DATABASE_APP
            )
                .fallbackToDestructiveMigration()
                .build()
    }
}

package com.example.elvo.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.elvo.contracts.RoomContract
import com.example.elvo.data.database.models.FaqEntity

@Dao
interface FaqDao {

    @Query("SELECT * FROM ${RoomContract.TABLE_FAQ}")
    suspend fun getAllFaq(): List<FaqEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFaqList(faqList: List<FaqEntity>)

    @Query("DELETE FROM ${RoomContract.TABLE_FAQ}")
    suspend fun clearFaq()
}
package com.example.elvo.data.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.elvo.contracts.RoomContract

@Entity(tableName = RoomContract.TABLE_FAQ)
data class FaqEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val question: String,
    val answer: String
)
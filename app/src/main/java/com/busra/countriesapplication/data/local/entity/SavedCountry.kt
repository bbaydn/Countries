package com.busra.countriesapplication.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "SavedCountry")
data class SavedCountry(
    @PrimaryKey
    val savedCode : String,
    val savedName : String,
    val saved : Boolean
)

package com.busra.countriesapplication.core.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.busra.countriesapplication.data.local.CountriesDAO
import com.busra.countriesapplication.data.local.entity.SavedCountry

@Database(
    entities = [SavedCountry::class],
    version = 1
)
abstract class CountriesDatabase : RoomDatabase() {

    abstract fun countriesDao() : CountriesDAO

}
package com.busra.countriesapplication.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.busra.countriesapplication.data.local.entity.SavedCountry

@Dao
interface CountriesDAO {

    @Query("SELECT * FROM SavedCountry")
    fun getAllSavedCountries() : LiveData<List<SavedCountry>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCountry(savedCountry: SavedCountry)

    @Delete
    suspend fun deleteCountry(savedCountry: SavedCountry)

}
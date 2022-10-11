package com.busra.countriesapplication.data.local

import com.busra.countriesapplication.data.local.entity.SavedCountry
import com.busra.countriesapplication.data.local.sp.SavedSharedPreferences
import javax.inject.Inject

class CountriesLocalDataSource @Inject constructor(
    private val countriesDAO: CountriesDAO,
    private val preferences: SavedSharedPreferences
) {

    fun getAllSavedCountries() =
        countriesDAO.getAllSavedCountries()

    suspend fun saveCountry(country: SavedCountry) =
        countriesDAO.saveCountry(country)

    suspend fun deleteCountry(country: SavedCountry) =
        countriesDAO.deleteCountry(country)

    fun getSavedInformation(countryCode : String) =
        preferences.getSavedInformation(countryCode)

    fun editSavedInformation(countryCode: String,save : Boolean) =
        preferences.editSavedInformation(countryCode,save)

    fun getSavedSharedPreferences() =
        preferences.savedSharedPreferences()
}
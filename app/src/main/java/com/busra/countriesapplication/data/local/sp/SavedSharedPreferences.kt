package com.busra.countriesapplication.data.local.sp

import android.content.SharedPreferences
import javax.inject.Inject


class SavedSharedPreferences @Inject constructor(
    private val preferences: SharedPreferences
) {

    fun savedSharedPreferences() = SavedSharedPreferences(preferences)

    fun getSavedInformation(countryCode : String) =
        preferences.getBoolean(countryCode,false)

    fun editSavedInformation(countryCode: String,save : Boolean) =
        preferences.edit().putBoolean(countryCode,save).apply()
}
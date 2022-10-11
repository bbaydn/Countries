package com.busra.countriesapplication.domain.mapper

import com.busra.countriesapplication.data.local.entity.SavedCountry
import com.busra.countriesapplication.data.remote.response.Country
import javax.inject.Inject

class CountryMapper @Inject constructor(){

    fun responseToEntity(
        saved : Boolean,
        response : Country
    ) : SavedCountry =
        with(response){
            SavedCountry(
                savedCode = code,
                savedName = name,
                saved = saved
            )
        }
}
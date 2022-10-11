package com.busra.countriesapplication.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.busra.countriesapplication.data.local.entity.SavedCountry
import com.busra.countriesapplication.data.remote.response.Country
import com.busra.countriesapplication.domain.repository.CountriesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedCountriesViewModel @Inject constructor(
    private val countriesRepository: CountriesRepository
) : ViewModel() {

    val getAllSavedCountries = countriesRepository.getAllSavedCountries()

    fun saveCountry(country: Country) =
        viewModelScope.launch(Dispatchers.IO) {
            countriesRepository.saveCountry(country)
        }

    fun deleteCountry(savedCountry: SavedCountry) =
        viewModelScope.launch(Dispatchers.IO) {
            countriesRepository.deleteCountry(savedCountry)
        }

    fun getSavedInformation(countryCode : String) =
        countriesRepository.getSavedInformation(countryCode)

    fun editSavedInformation(countryCode: String,save : Boolean) =
        countriesRepository.editSavedInformation(countryCode,save)

}
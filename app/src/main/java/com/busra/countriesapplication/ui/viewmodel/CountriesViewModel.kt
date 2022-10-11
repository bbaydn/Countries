package com.busra.countriesapplication.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.busra.countriesapplication.domain.repository.CountriesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CountriesViewModel @Inject constructor(
    private val countriesRepository: CountriesRepository
) : ViewModel() {

    val countries = countriesRepository.getCountries().cachedIn(viewModelScope)

    fun editSavedInformation(countryCode: String,save : Boolean) =
        countriesRepository.editSavedInformation(countryCode,save)

}
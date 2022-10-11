package com.busra.countriesapplication.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.busra.countriesapplication.data.remote.response.CountryDetail
import com.busra.countriesapplication.domain.repository.CountriesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val countriesRepository: CountriesRepository
) : ViewModel(){

    private val countryDetail = MutableLiveData<CountryDetail>()


    fun getCountryInformation() : LiveData<CountryDetail> = countryDetail
    fun getLoadingInformation() : LiveData<Boolean> = countriesRepository.getIsLoading()

    fun getCountryDetail(code : String) {
        countriesRepository.getCountryDetails(code,countryDetail)
    }

}
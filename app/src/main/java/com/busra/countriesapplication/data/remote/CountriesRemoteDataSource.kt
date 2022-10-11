package com.busra.countriesapplication.data.remote

import com.busra.countriesapplication.data.remote.response.CountryDetailData
import com.busra.countriesapplication.data.remote.response.CountryResponse
import retrofit2.Call
import javax.inject.Inject

class CountriesRemoteDataSource @Inject constructor(
    private val countriesService: CountriesService
) {

    suspend fun getCountries(offset : Int, limit: Int) : CountryResponse =
        countriesService.getCountries(offset,limit)

    fun getCountryDetails(code : String) : Call<CountryDetailData> =
        countriesService.getCountryDetails(code)
}
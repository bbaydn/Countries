package com.busra.countriesapplication.domain.repository


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.busra.countriesapplication.data.local.CountriesLocalDataSource
import com.busra.countriesapplication.data.local.entity.SavedCountry
import com.busra.countriesapplication.data.remote.CountriesRemoteDataSource
import com.busra.countriesapplication.data.remote.response.Country
import com.busra.countriesapplication.data.remote.response.CountryDetailData
import com.busra.countriesapplication.data.remote.response.CountryDetail
import com.busra.countriesapplication.domain.mapper.CountryMapper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class CountriesRepository @Inject constructor(
    private val remoteCountriesDataSource: CountriesRemoteDataSource,
    private val localCountriesLocalDataSource: CountriesLocalDataSource,
    private val countryMapper: CountryMapper
) {

    private val isLoading = MutableLiveData<Boolean>()
    fun getIsLoading() : LiveData<Boolean> = isLoading

    fun getCountries() =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { CountriesPagingSource(remoteCountriesDataSource)}
        ).liveData

    fun getCountryDetails(code : String, liveData: MutableLiveData<CountryDetail>){
        isLoading.postValue(false)
        remoteCountriesDataSource.getCountryDetails(code).enqueue(object: Callback<CountryDetailData> {
            override fun onResponse(call: Call<CountryDetailData>, response: Response<CountryDetailData>) {
                if(response.isSuccessful){
                    liveData.postValue(response.body()?.data)
                    isLoading.postValue(true)
                }
            }

            override fun onFailure(call: Call<CountryDetailData>, t: Throwable) {
                liveData.postValue(null)
                isLoading.postValue(false)
            }
        })
    }

    fun getAllSavedCountries() : LiveData<List<SavedCountry>> =
        localCountriesLocalDataSource.getAllSavedCountries()

    suspend fun saveCountry(country: Country) =
        localCountriesLocalDataSource.saveCountry(countryMapper.responseToEntity(true,country))

    suspend fun deleteCountry(savedCountry: SavedCountry) =
        localCountriesLocalDataSource.deleteCountry(savedCountry)

    fun getSavedInformation(countryCode : String) =
        localCountriesLocalDataSource.getSavedInformation(countryCode)

    fun editSavedInformation(countryCode: String,save : Boolean) =
        localCountriesLocalDataSource.editSavedInformation(countryCode,save)

    fun getSavedSharedPreferences() =
        localCountriesLocalDataSource.getSavedSharedPreferences()
}
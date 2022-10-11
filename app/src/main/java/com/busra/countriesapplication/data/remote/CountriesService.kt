package com.busra.countriesapplication.data.remote

import com.busra.countriesapplication.data.remote.response.CountryDetailData
import com.busra.countriesapplication.data.remote.response.CountryResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface CountriesService {

    @Headers(
        "X-RapidAPI-Key: 8af70c6840mshbaceab275567d97p1a0748jsn0a164117b1fb",
        "X-RapidAPI-Host: wft-geo-db.p.rapidapi.com"
    )
    @GET("countries")
    suspend fun getCountries(
        @Query("offset") offset : Int,
        @Query("limit") limit : Int
    ) : CountryResponse

    @Headers(
        "X-RapidAPI-Key: 8af70c6840mshbaceab275567d97p1a0748jsn0a164117b1fb",
        "X-RapidAPI-Host: wft-geo-db.p.rapidapi.com"
    )
    @GET("countries/{code}")
    fun getCountryDetails(
        @Path("code") code : String
    ) : Call<CountryDetailData>
}
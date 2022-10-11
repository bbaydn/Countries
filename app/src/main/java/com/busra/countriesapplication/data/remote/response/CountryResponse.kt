package com.busra.countriesapplication.data.remote.response

data class CountryResponse(
    val data : List<Country>
)


data class Country(
    val code : String,
    val name : String
){
}

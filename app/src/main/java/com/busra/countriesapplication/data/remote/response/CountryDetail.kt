package com.busra.countriesapplication.data.remote.response

import com.google.gson.annotations.SerializedName

data class CountryDetailData(
    val data : CountryDetail
)
data class CountryDetail(
    @SerializedName("name") val name : String,
    @SerializedName("flagImageUri") val image: String,
    val wikiDataId : String,
    val code : String
){
}
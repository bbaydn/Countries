package com.busra.countriesapplication.di

import android.content.SharedPreferences
import com.busra.countriesapplication.core.data.CountriesDatabase
import com.busra.countriesapplication.data.local.CountriesDAO
import com.busra.countriesapplication.data.local.sp.SavedSharedPreferences
import com.busra.countriesapplication.data.remote.CountriesService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit


@Module
@InstallIn(SingletonComponent::class)
object CountriesAppModule {

    @Provides
    fun provideMovieService(retrofit: Retrofit): CountriesService =
        retrofit.create(CountriesService::class.java)

    @Provides
    fun provideCountriesDao(countriesDatabase: CountriesDatabase) : CountriesDAO =
        countriesDatabase.countriesDao()

    @Provides
    fun provideSavedSharedPreferences(sharedPreferences: SharedPreferences) =
        SavedSharedPreferences(sharedPreferences)

}
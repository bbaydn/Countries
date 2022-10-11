package com.busra.countriesapplication.core.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.busra.countriesapplication.core.data.CountriesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDataModule {

    @Provides
    @Singleton
    fun provideCountriesDatabase(
        @ApplicationContext context: Context
    ) : CountriesDatabase =
        Room.databaseBuilder(context, CountriesDatabase::class.java, "countries_db")
            .build()

    @Provides
    @Singleton
    fun provideSharedPreferences(
        @ApplicationContext context: Context
    ) : SharedPreferences =
        context.getSharedPreferences("saved",Context.MODE_PRIVATE)

}
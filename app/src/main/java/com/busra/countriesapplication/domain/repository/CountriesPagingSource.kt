package com.busra.countriesapplication.domain.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.busra.countriesapplication.data.remote.CountriesRemoteDataSource
import com.busra.countriesapplication.data.remote.response.Country
import retrofit2.HttpException
import java.io.IOException

private const val COUNTRIES_START_OFFSET = 0

class CountriesPagingSource (
    private val countriesRemoteDataSource: CountriesRemoteDataSource
    ) : PagingSource<Int, Country>() {
    override fun getRefreshKey(state: PagingState<Int, Country>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Country> {
        val position = (params.key) ?: COUNTRIES_START_OFFSET

        return try {
            val response = countriesRemoteDataSource.getCountries(position,10)
            val country = response.data

            LoadResult.Page(
                data = country,
                prevKey = if(position == COUNTRIES_START_OFFSET) null else position - 10,
                nextKey = if(country.isEmpty()) null else position + 10
            )
        }catch (exception : IOException){
            LoadResult.Error(exception)
        }catch (exception : HttpException){
            LoadResult.Error(exception)
        }
    }

}
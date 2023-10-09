package com.example.worldexplorer.data.network

import com.example.worldexplorer.data.model.CountryModel
import retrofit2.http.GET
import retrofit2.http.Query

interface RestCountriesApiClient {
    @GET("all")
    suspend fun getAllCountries(
        @Query("fields") fields: String
    ) : List<CountryModel>
}
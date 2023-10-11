package com.example.worldexplorer.data.network

import com.example.worldexplorer.data.network.responses.countries.CountryResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RestCountriesApiService {
    @GET("all")
    suspend fun getAllCountries(
        @Query("fields") fields: String
    ) : List<CountryResponse>

    @GET("name/{country}")
    suspend fun getCountryInfo(
        @Path("country") country: String,
        @Query("fields") fields: String
    )
}
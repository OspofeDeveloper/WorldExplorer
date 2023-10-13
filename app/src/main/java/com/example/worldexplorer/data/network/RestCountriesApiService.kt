package com.example.worldexplorer.data.network

import com.example.worldexplorer.data.network.responses.countries.CountryResponse
import com.example.worldexplorer.data.network.responses.detailCountries.DetailCountriesResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface RestCountriesApiService {
    @GET("independent?status=true")
    suspend fun getAllCountries(): List<CountryResponse>

    @GET("alpha/{cca2}")
    suspend fun getDetailCountries(
        @Path("cca2") country: String
    ): List<DetailCountriesResponse>
}
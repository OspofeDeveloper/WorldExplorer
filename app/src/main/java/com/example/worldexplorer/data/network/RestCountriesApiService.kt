package com.example.worldexplorer.data.network

import com.example.worldexplorer.data.network.response.CountryResponse
import retrofit2.http.GET

interface RestCountriesApiService {
    @GET("independent?status=true")
    suspend fun getAllCountries(): List<CountryResponse>


    /*
    @GET("alpha/{cca2}")
    suspend fun getDetailCountries(
        @Path("cca2") country: String
    ): List<DetailCountriesResponse>

     */
}
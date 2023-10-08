package com.example.worldexplorer.data.network

import com.example.worldexplorer.data.model.CountryArraylistModel
import retrofit2.http.GET

interface RestCountriesApiClient {
    @GET("all")
    suspend fun getAllCountries() : CountryArraylistModel
}
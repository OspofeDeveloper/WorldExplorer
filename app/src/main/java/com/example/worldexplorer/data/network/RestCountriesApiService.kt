package com.example.worldexplorer.data.network

import com.example.worldexplorer.data.network.response.WorldExplorerResponse
import retrofit2.http.GET

interface RestCountriesApiService {
    @GET("independent?status=true&fields=name,cca2,area,borders,capital,cca3,continents,population,region")
    suspend fun getWorldExplorerInfoFromAPI(): List<WorldExplorerResponse>
}

/**
@GET("alpha/{cca2}")
suspend fun getDetailCountries(@Path("cca2") country: String): List<DetailCountriesResponse>
 */
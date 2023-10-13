package com.example.worldexplorer.data.network.responses.detailCountries

import com.example.worldexplorer.domain.models.detailcountries.DetailCountriesModel
import com.google.gson.annotations.SerializedName

data class DetailCountriesResponse(
    @SerializedName("area") var area: Double,
    @SerializedName("borders") var borders: List<String>,
    @SerializedName("capital") var capital: List<String>,
    @SerializedName("cca3") var cca3: String,
    @SerializedName("continents") var continents: List<String>,
    @SerializedName("population") var population: Int,
) {
    fun toDomain(): DetailCountriesModel =
        DetailCountriesModel(
            area = area,
            borders = borders,
            capital = capital,
            cca3 = cca3,
            continents = continents,
            population = population
        )
}
package com.example.worldexplorer.data.network.response

import com.example.worldexplorer.domain.models.countries.CountriesModel
import com.google.gson.annotations.SerializedName
import java.text.Normalizer

/**
    Aunque se llamen igual las variables se ponen los SerializedName porque cuando lanzamos la app
    a produccion hacemos cosas como la ofuscacion, que se trata de cambiar los nombres a las
    variables a otros mas cortos y no tan claros para asi optimizarla porque pesa menos y si alguien
    intenta hackear el codigo el codigo no se entiende
*/
data class CountryResponse(
    @SerializedName("cca2") val cca2: String,
    @SerializedName("name") val name: Name,
    @SerializedName("area") var area: Double?,
    @SerializedName("borders") var borders: List<String>?,
    @SerializedName("capital") var capital: List<String>?,
    @SerializedName("cca3") var cca3: String?,
    @SerializedName("continents") var continents: List<String>?,
    @SerializedName("population") var population: Int?,
    @SerializedName("region") var region: String?
) {
    fun toDomain(): CountriesModel =
        CountriesModel(
            cca2 = cca2,
            name = Normalizer.normalize(name.common, Normalizer.Form.NFD),
            area = area,
            borders = borders,
            capital = capital?.joinToString(separator = ", "),
            cca3 = cca3,
            continents = continents?.joinToString(separator = ", "),
            population = population,
            region = region
        )
}
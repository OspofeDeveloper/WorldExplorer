package com.example.worldexplorer.data.network.response

import com.example.worldexplorer.data.database.entities.BorderEntity
import com.example.worldexplorer.data.database.entities.CountryEntity
import com.google.gson.annotations.SerializedName
import java.text.Normalizer

data class WorldExplorerResponse(
    @SerializedName("cca2") val cca2: String,
    @SerializedName("name") val name: Name,
    @SerializedName("area") var area: Double?,
    @SerializedName("capital") var capital: List<String>?,
    @SerializedName("cca3") var cca3: String,
    @SerializedName("continents") var continents: List<String>?,
    @SerializedName("population") var population: Int?,
    @SerializedName("region") var region: String?,
    @SerializedName("borders") var borders: List<String>?
) {

    fun toCountryEntity(): CountryEntity =
        CountryEntity(
            cca2 = cca2,
            name = Normalizer.normalize(name.common, Normalizer.Form.NFD),
            area = area,
            capital = capital?.joinToString(separator = ", "),
            cca3 = cca3,
            continents = continents?.joinToString(separator = ", "),
            population = population,
            region = region
        )

    fun toBorderEntity(): BorderEntity =
        BorderEntity(
            cca3 = cca3,
            cca2 = cca2,
            name = Normalizer.normalize(name.common, Normalizer.Form.NFD)
        )

}
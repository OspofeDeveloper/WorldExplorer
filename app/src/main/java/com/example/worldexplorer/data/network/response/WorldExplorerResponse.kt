package com.example.worldexplorer.data.network.response

import com.example.worldexplorer.data.database.entities.BorderEntity
import com.example.worldexplorer.data.database.entities.CountryBasicEntity
import com.example.worldexplorer.data.database.entities.CountryDetailEntity
import com.example.worldexplorer.data.database.entities.relations.CountryDetailBorderCrossRef
import com.google.gson.annotations.SerializedName
import java.text.Normalizer

/**
Aunque se llamen igual las variables se ponen los SerializedName porque cuando lanzamos la app
a produccion hacemos cosas como la ofuscacion, que se trata de cambiar los nombres a las
variables a otros mas cortos y no tan claros para asi optimizarla porque pesa menos y si alguien
intenta hackear el codigo el codigo no se entiende
 */
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
    fun toCountryBasicEntity(): CountryBasicEntity =
        CountryBasicEntity(
            cca2 = cca2,
            name = Normalizer.normalize(name.common, Normalizer.Form.NFD),
        )

    fun toCountryDetailEntity(): CountryDetailEntity =
        CountryDetailEntity(
            cca2 = cca2,
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
package com.example.worldexplorer.data.network.responses.countries

import com.example.worldexplorer.domain.models.countries.CountriesModel
import com.google.gson.annotations.SerializedName
import java.text.Normalizer

/*
    Aunque se llamen igual las variables se ponen los SerializedName porque cuando lanzamos la app
    a produccion hacemos cosas como la ofuscacion, que se trata de cambiar los nombres a las
    variables a otros mas cortos y no tan claros para asi optimizarla porque pesa menos y si alguien
    intenta hackear el codigo el codigo no se entiende
*/
data class CountryResponse(
    @SerializedName("cca2") val cca2: String,
    @SerializedName("name") var name: Name,
) {
    fun toDomain() : CountriesModel =
        CountriesModel(
            "https://flagcdn.com/w320/${cca2.lowercase()}.png",
            Normalizer.normalize(name.common, Normalizer.Form.NFD)
        )
}
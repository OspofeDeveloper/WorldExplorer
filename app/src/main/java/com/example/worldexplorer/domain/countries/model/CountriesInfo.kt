package com.example.worldexplorer.domain.countries.model

import com.example.worldexplorer.data.countries.database.entities.CountryEntity
import com.example.worldexplorer.data.countries.model.CountryModel
import java.text.Normalizer

data class CountriesInfo (
    var flagImageUrl: String,
    var name: String,
)

fun CountryModel.toDomain() : CountriesInfo =
    CountriesInfo(
        "https://flagcdn.com/w320/${cca2.lowercase()}.png",
        Normalizer.normalize(name.common, Normalizer.Form.NFD))


fun CountryEntity.toDomain() : CountriesInfo =
    CountriesInfo("https://flagcdn.com/w320/${cca2.lowercase()}.png", name)
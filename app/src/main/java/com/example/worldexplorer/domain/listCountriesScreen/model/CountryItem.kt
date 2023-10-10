package com.example.worldexplorer.domain.listCountriesScreen.model

import com.example.worldexplorer.data.database.entities.CountryEntity
import com.example.worldexplorer.data.model.CountryModel
import java.text.Normalizer

data class CountryItem (
    var flagImageUrl: String,
    var name: String,
)

fun CountryModel.toDomain() : CountryItem =
    CountryItem(
        "https://flagcdn.com/w320/${cca2.lowercase()}.png",
        Normalizer.normalize(name.common, Normalizer.Form.NFD))


fun CountryEntity.toDomain() : CountryItem =
    CountryItem("https://flagcdn.com/w320/${cca2.lowercase()}.png", name)
package com.example.worldexplorer.domain.model

import com.example.worldexplorer.data.database.entities.CountryEntity
import com.example.worldexplorer.data.model.CountryModel

data class CountryItem (
    var flagImageUrl: String,
    var name: String,
)

fun CountryModel.toDomain() : CountryItem =
    CountryItem(
        "https://flagcdn.com/w320/${cca2.lowercase()}.png",
        java.text.Normalizer.normalize(name.common, java.text.Normalizer.Form.NFD))


fun CountryEntity.toDomain() : CountryItem =
    CountryItem("https://flagcdn.com/w320/${cca2.lowercase()}.png", name)
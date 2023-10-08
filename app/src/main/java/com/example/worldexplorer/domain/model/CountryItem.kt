package com.example.worldexplorer.domain.model

import com.example.worldexplorer.data.model.CountryItemModel

data class CountryItem (
    var flagImageUrl: String,
    var name: String,
)

fun CountryItemModel.toDomain() : CountryItem {
    val flagUrl = "https://flagcdn.com/w320/${cca2.lowercase()}.png"
    return CountryItem(flagUrl, name.common)
}
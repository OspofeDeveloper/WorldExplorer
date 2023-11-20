package com.example.worldexplorer.domain.models.detailcountries

data class CountryDetailModel(
    var area: Double?,
    var capital: String?,
    var cca3: String?,
    var continents: String?,
    var population: Int?,
    var region: String?,
    var imageUrl: String,
    var cca2Borders: List<String>,
    var nameBorders: List<String>
)

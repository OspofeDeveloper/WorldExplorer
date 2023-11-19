package com.example.worldexplorer.domain.models.detailcountries

import com.example.worldexplorer.data.database.entities.CountryDetailEntity

data class CountryDetailModel(
    var cca2: String,
    var area: Double?,
    var capital: String?,
    var cca3: String?,
    var continents: String?,
    var population: Int?,
    var region: String?,
    var cca2Borders: List<String>,
    var nameBorders: List<String>
)

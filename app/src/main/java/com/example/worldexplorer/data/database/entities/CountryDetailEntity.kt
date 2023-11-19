package com.example.worldexplorer.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.worldexplorer.domain.models.countries.CountryDetailModel

@Entity(tableName = "country_detail_table")
data class CountryDetailEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "cca2") val cca2: String,
    @ColumnInfo(name = "area") val area: Double?,
    @ColumnInfo(name = "capital") val capital: String?,
    @ColumnInfo(name = "cca3") val cca3: String?,
    @ColumnInfo(name = "continents") val continents: String?,
    @ColumnInfo(name = "population") val population: Int?,
    @ColumnInfo(name = "region") val region: String?,
) {
    fun toDomain(): CountryDetailModel =
        CountryDetailModel(
            cca2 = cca2,
            area = area,
            capital = capital,
            cca3 = cca3,
            continents = continents,
            population = population,
            region = region
        )
}
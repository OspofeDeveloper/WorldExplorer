package com.example.worldexplorer.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.worldexplorer.domain.models.countries.CountriesModel

@Entity(tableName = "country_table")
data class CountriesEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "cca2") val cca2: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "area") val area: Double?,
    @ColumnInfo(name = "borders") val borders: String?,
    @ColumnInfo(name = "capital") val capital: String?,
    @ColumnInfo(name = "cca3") val cca3: String?,
    @ColumnInfo(name = "continents") val continents: String?,
    @ColumnInfo(name = "population") val population: Int?,
) {
    fun toDomain(): CountriesModel =
        CountriesModel(
            cca2 = cca2,
            name = name,
            area = area,
            borders = borders,
            capital = capital,
            cca3 = cca3,
            continents = continents,
            population = population
        )
}
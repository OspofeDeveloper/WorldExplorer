package com.example.worldexplorer.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.worldexplorer.domain.models.countries.CountryBasicModel

@Entity(tableName = "country_table")
data class CountryEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "cca2") val cca2: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "area") val area: Double?,
    @ColumnInfo(name = "capital") val capital: String?,
    @ColumnInfo(name = "cca3") val cca3: String?,
    @ColumnInfo(name = "continents") val continents: String?,
    @ColumnInfo(name = "population") val population: Int?,
    @ColumnInfo(name = "region") val region: String?,
) {
    fun toCountryBasicModel() : CountryBasicModel =
        CountryBasicModel(
            cca2 = cca2,
            name = name
        )
}
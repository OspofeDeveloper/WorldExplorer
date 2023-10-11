package com.example.worldexplorer.data.countries.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.worldexplorer.domain.countries.model.CountriesInfo

@Entity(tableName = "country_table")
data class CountryEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name= "id") val id: Int = 0,
    @ColumnInfo(name = "cca2") val cca2: String,
    @ColumnInfo(name = "name") val name: String
)

fun CountriesInfo.toDatabase(): CountryEntity {
    val cca2 = flagImageUrl.dropLast(4).takeLastWhile { it.isLetter() }
    return CountryEntity(name = name, cca2 = cca2)
}
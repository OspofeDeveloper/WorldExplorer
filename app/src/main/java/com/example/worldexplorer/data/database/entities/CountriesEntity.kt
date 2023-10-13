package com.example.worldexplorer.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.worldexplorer.domain.models.countries.CountriesModel

@Entity(tableName = "country_table")
data class CountriesEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name= "id") val id: Int = 0,
    @ColumnInfo(name = "cca2") val cca2: String,
    @ColumnInfo(name = "name") val name: String
) {
    fun toDomain() : CountriesModel =
        CountriesModel(
            cca2 = cca2,
            name = name,
        )
}
package com.example.worldexplorer.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.worldexplorer.domain.models.countries.CountryBasicModel
import com.example.worldexplorer.domain.models.detailquiz.QuizDetailModel
import com.example.worldexplorer.domain.models.detailquiz.QuizOptionModel

@Entity(tableName = "country_basic_table")
data class CountryBasicEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "cca2") val cca2: String,
    @ColumnInfo(name = "name") val name: String
) {
    fun toDomain(): CountryBasicModel =
        CountryBasicModel(
            cca2 = cca2,
            name = name
        )
}
package com.example.worldexplorer.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.worldexplorer.data.database.entities.CountryEntity

@Dao
interface CountryDao {

    @Query("SELECT * FROM country_table")
    suspend fun getAllCountries():List<CountryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(countries:List<CountryEntity>)

    @Query("DELETE FROM country_table")
    suspend fun deleteAllCountries()
}
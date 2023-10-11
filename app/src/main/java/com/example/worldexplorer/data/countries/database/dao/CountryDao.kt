package com.example.worldexplorer.data.countries.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.worldexplorer.data.countries.database.entities.CountryEntity

@Dao
interface CountryDao {

    @Query("SELECT * FROM country_table")
    suspend fun getAllCountries():List<CountryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(countries:List<CountryEntity>)

    @Query("DELETE FROM country_table")
    suspend fun deleteAllCountries()

    @Query("SELECT * FROM country_table ORDER BY name ASC")
    suspend fun getAllCountriesOrderAsc():List<CountryEntity>

    @Query("SELECT * FROM country_table ORDER BY name DESC")
    suspend fun getAllCountriesOrderDesc():List<CountryEntity>
}
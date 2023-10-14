package com.example.worldexplorer.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.worldexplorer.data.database.entities.CountriesEntity

@Dao
interface CountriesDao {

    @Query("SELECT * FROM country_table")
    suspend fun getAllCountries():List<CountriesEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(countries:List<CountriesEntity>)

    @Query("DELETE FROM country_table")
    suspend fun deleteAllCountries()

    @Query("SELECT * FROM country_table ORDER BY name ASC")
    suspend fun getAllCountriesOrderAsc():List<CountriesEntity>

    @Query("SELECT * FROM country_table ORDER BY name DESC")
    suspend fun getAllCountriesOrderDesc():List<CountriesEntity>
    @Query("SELECT * FROM country_table WHERE cca2 = :cca2Code")
    suspend fun getCountriesInfo(cca2Code: String): CountriesEntity
}
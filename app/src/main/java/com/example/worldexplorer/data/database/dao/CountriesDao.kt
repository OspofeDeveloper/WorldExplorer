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
    suspend fun getCountriesInfo(cca2Code: String): CountriesEntity?

    @Query("SELECT cca2 FROM country_table WHERE cca3 = :cca3Code")
    suspend fun getBorderCca2(cca3Code: String): String

    @Query("SELECT name FROM country_table WHERE cca3 = :cca3Code")
    suspend fun getBorderName(cca3Code: String): String

    @Query("SELECT * FROM country_table WHERE region = :region")
    suspend fun getCountriesByRegion(region: String):List<CountriesEntity>

    @Query("SELECT name FROM country_table WHERE cca2 = :cca2")
    suspend fun getNameFromCca2(cca2: String): String

    @Query("SELECT cca2 FROM country_table ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomCca2(): String
}
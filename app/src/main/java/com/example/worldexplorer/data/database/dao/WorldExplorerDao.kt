package com.example.worldexplorer.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.worldexplorer.data.database.entities.BorderEntity
import com.example.worldexplorer.data.database.entities.CountryEntity
import com.example.worldexplorer.data.database.entities.relations.CountryDetailBorderCrossRef
import com.example.worldexplorer.data.database.entities.relations.CountryDetailWithBorder

@Dao
interface WorldExplorerDao {

    /** Insert Queries */

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCountry(countries: List<CountryEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllBorders(borders: List<BorderEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCountryDetailBorderCrossRef(crossRef: CountryDetailBorderCrossRef)

    /** Delete Queries */

    @Query("DELETE FROM country_table")
    suspend fun deleteAllCountry()

    @Query("DELETE FROM borders_table")
    suspend fun deleteAllBorder()

    @Query("DELETE FROM detail_borders_cross_ref_table")
    suspend fun deleteAllDetailBorderCrossRef()

    @Transaction
    @Query("SELECT * FROM country_table WHERE cca2 = :cca2")
    suspend fun getBordersOfCountryDetail(cca2: String): CountryDetailWithBorder

    /** Country Screen Queries */

    @Query("SELECT name, cca2 FROM country_table")
    suspend fun getAllBasicCountries(): List<CountryEntity>

    /** Quiz Detail Screen Queries */

    @Query("SELECT name, cca2 FROM country_table WHERE cca2 NOT IN (:correctCca2List) ORDER BY RANDOM() LIMIT 4")
    suspend fun getQuizOptionsGlobal(correctCca2List: List<String>): List<CountryEntity>

    @Transaction
    @Query("SELECT name, cca2 FROM country_table WHERE region = :region AND cca2 NOT IN (:correctCca2List) ORDER BY RANDOM() LIMIT 4")
    suspend fun getQuizOptionsByRegion(correctCca2List: List<String>, region: String): List<CountryEntity>

    /** Travel Screen Queries */

    @Query("SELECT name, cca2 FROM country_table ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomCca2(): CountryEntity
}
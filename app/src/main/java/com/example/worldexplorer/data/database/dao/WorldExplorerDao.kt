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


    /** Ponemos la anotación @Transaction en un método que realice operaciones en varias tablas
     *  relacionadas en una transacción única. Esto garantizará que ambas operaciones se realicen
     *  de manera atómica, es decir, ambas se completan o ninguna se completa */

    /** En el caso de querer el CountryDetail a partir de cca2 hago la petición para eso, y como le
     *  digo que me devuelva CountryDetailWithBorder, Room me busca y devuelve también los Border.
     *
     *  (en este caso como yo se que solo me va a devolver un elemento en country_detail_table ya
     *  que los cca2 son unicos, indico que me devuelve CountryDetailWithBorder. En caso de que
     *  tuviese que filtrar por un parámetro que no es único como el continente, ahi la peticion me
     *  devolverá List<CountryDetailWithBorder>) */
    @Transaction
    @Query("SELECT * FROM country_table WHERE cca2 = :cca2")
    suspend fun getBordersOfCountryDetail(cca2: String): CountryDetailWithBorder


    /** Peticiones para Country Fragment*/
    @Query("SELECT name, cca2 FROM country_table")
    suspend fun getAllBasicCountries(): List<CountryEntity>

    @Query("SELECT name, cca2 FROM country_table ORDER BY name ASC")
    suspend fun getAllCountriesBasicOrderAsc(): List<CountryEntity>

    @Query("SELECT * FROM country_table ORDER BY name DESC")
    suspend fun getAllCountriesBasicOrderDesc(): List<CountryEntity>

    /** Peticiones para Quiz Detail Fragment */
    @Query("SELECT name, cca2 FROM country_table WHERE cca2 NOT IN (:correctCca2List) ORDER BY RANDOM() LIMIT 4")
    suspend fun getQuizOptionsGlobal(correctCca2List: List<String>): List<CountryEntity>

    @Transaction
    @Query("SELECT name, cca2 FROM country_table WHERE region = :region AND cca2 NOT IN (:correctCca2List) ORDER BY RANDOM() LIMIT 4")
    suspend fun getQuizOptionsByRegion(correctCca2List: List<String>, region: String): List<CountryEntity>

    /** Operaciones anteriores */
    @Query("SELECT name, cca2 FROM country_table ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomCca2(): CountryEntity
}
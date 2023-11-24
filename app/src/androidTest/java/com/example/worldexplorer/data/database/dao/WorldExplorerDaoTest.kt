package com.example.worldexplorer.data.database.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.worldexplorer.data.database.WorldExplorerDatabase
import com.example.worldexplorer.motherobject.WorldExplorerMotherObject
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * JUnit esta preparado para testear lenguajes de programacion que se ejecuten en jvm (Java, Kotlin...)
 * Como lo que queremos testear es Room que está en el contexto de Android necesitamos indicarselo.
 * Estos son Instrumented Unit Test
 * Con @SmallTest indicamos a JUnit que vamos a escribir Unit Test, no de Integració ni UI.
 *      Para Integration Tests -> @MediumTest
 *      Para UI Tests -> @LargeTest
 */
@RunWith(AndroidJUnit4::class)
@SmallTest
class WorldExplorerDaoTest {

    private lateinit var database: WorldExplorerDatabase
    private lateinit var dao: WorldExplorerDao

    /**
     * Al definir database no usamos databaseBuilder como hacemos para crear nuestra BD, sino que
     * usamos inMemoryDatabaseBuilder de tal forma que realmente la estamos solamente creando en
     * memoria en lugar de dentro de la "Persistance Storage", de tal forma que al solo estar en
     * memoria solamente se guarda para hacer los test.
     *
     * Tambien usamos allowMainThreadQueries, que de normal es algo que no queremos ya que en nuestro
     * codgo de la app queremos hacer las queries desde corrutinas para que no bloquee nuestro hilo
     * principal. En cambio en los test queremos que no haya multithreads ya que el uso de estos es
     * impredecible y en testing tenemos que estar en un entorno controlado.
     */
    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            WorldExplorerDatabase::class.java
        ).allowMainThreadQueries().build()

        dao = database.getWorldExplorerDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    /**
     * Como necesitamos ejecutar las funciones del Dao desde una corrutina pero no queremos
     * multithreading, usaremos runTest que es la forma de lanzar una corrutina desde el main
     * thread
     */
    @Test
    fun insertAllCountry() = runTest {
        val countryEntityList = WorldExplorerMotherObject.genericCountryEntityList
        val countryBasicModelList = WorldExplorerMotherObject.genericCountryBasicModelList

        dao.insertAllCountry(countryEntityList)
        val allBasicCountries = dao.getAllBasicCountries()

        assertThat(allBasicCountries).isEqualTo(countryBasicModelList)
    }

    /**
     * En esta funcion tambien hemos testeado insertCountryDetailBorderCrossRef ya que
     * son retroactivas, necesitamos las dos y si no funciona una la otra tampoco funciona.
     */
    @Test
    fun insertAllBorders() = runTest {
        //Given
        val countries = WorldExplorerMotherObject.genericCountryEntityList
        val borders = WorldExplorerMotherObject.genericBorderEntityList
        val crossRef = WorldExplorerMotherObject.genericCountryDetailBorderCrossRef

        //When
        dao.insertAllBorders(borders)
        dao.insertAllCountry(countries)
        crossRef.forEach {
            dao.insertCountryDetailBorderCrossRef(it)
        }

        val allBorders = dao.getBordersOfCountryDetail("ES")

        //Then
        assertThat(allBorders.border).doesNotContain(borders.first())
        assertThat(allBorders.border).contains(borders[1])
        assertThat(allBorders.border).contains(borders[2])
    }

    /**
     * Como ya hemos testeado que nos devuelva bien los borders ahora queda testear
     * que también nos devuelva el countryDetail correcto
     */
    @Test
    fun getBordersOfCountryDetail() = runTest {
        //Given
        val countries = WorldExplorerMotherObject.genericCountryEntityList
        val borders = WorldExplorerMotherObject.genericBorderEntityList
        val crossRef = WorldExplorerMotherObject.genericCountryDetailBorderCrossRef

        //When
        dao.insertAllBorders(borders)
        dao.insertAllCountry(countries)
        crossRef.forEach {
            dao.insertCountryDetailBorderCrossRef(it)
        }

        val allBorders = dao.getBordersOfCountryDetail("ES")

        //Then
        assertThat(allBorders.countryDetail).isEqualTo(countries.first())
    }

    @Test
    fun deleteAllCountry() = runTest {
        val countryEntityList = WorldExplorerMotherObject.genericCountryEntityList

        dao.insertAllCountry(countryEntityList)
        dao.deleteAllCountry()
        val allBasicCountries = dao.getAllBasicCountries()

        assertThat(allBasicCountries).isEmpty()
    }

    @Test
    fun deleteAllBorder() = runTest {
        //Given
        val countries = WorldExplorerMotherObject.genericCountryEntityList
        val borders = WorldExplorerMotherObject.genericBorderEntityList
        val crossRef = WorldExplorerMotherObject.genericCountryDetailBorderCrossRef

        //When
        dao.insertAllBorders(borders)
        dao.insertAllCountry(countries)
        crossRef.forEach {
            dao.insertCountryDetailBorderCrossRef(it)
        }

        dao.deleteAllBorder()

        val allBorders = dao.getBordersOfCountryDetail("ES")

        //Then
        assertThat(allBorders.countryDetail).isEqualTo(countries.first())
        assertThat(allBorders.border).isEmpty()
    }

    @Test
    fun deleteAllDetailBorderCrossRef() = runTest {
        //Given
        val countries = WorldExplorerMotherObject.genericCountryEntityList
        val borders = WorldExplorerMotherObject.genericBorderEntityList
        val crossRef = WorldExplorerMotherObject.genericCountryDetailBorderCrossRef

        //When
        dao.insertAllBorders(borders)
        dao.insertAllCountry(countries)
        crossRef.forEach {
            dao.insertCountryDetailBorderCrossRef(it)
        }

        dao.deleteAllDetailBorderCrossRef()

        val allBorders = dao.getBordersOfCountryDetail("ES")

        //Then
        assertThat(allBorders.countryDetail).isEqualTo(countries.first())
        assertThat(allBorders.border).isEmpty()
    }

    @Test
    fun getQuizOptionsGlobalWithEmptyCorrectOptionsList_returnsCountriesFromCountryTable() =
        runTest {
            val countryEntityList = WorldExplorerMotherObject.genericCountryEntityList
            val countryBasicModelList = WorldExplorerMotherObject.genericCountryBasicModelList


            dao.insertAllCountry(countryEntityList)
            val quizOptions = dao.getQuizOptionsGlobal(emptyList())

            quizOptions.forEach {
                assertThat(countryBasicModelList).contains(it)
            }
        }

    @Test
    fun getQuizOptionsGlobalWithEmptyCorrectOptionsList_returns4countries() = runTest {
        val countryEntityList = WorldExplorerMotherObject.genericCountryEntityList

        dao.insertAllCountry(countryEntityList)
        val quizOptions = dao.getQuizOptionsGlobal(emptyList())

        assertThat(quizOptions.size).isEqualTo(4)
    }

    @Test
    fun getQuizOptionsGlobalWithCorrectOptionsList_returnsDifferentThanCorrectOptions() = runTest {
        val countryEntityList = WorldExplorerMotherObject.genericCountryEntityList
        val resultCountryList = WorldExplorerMotherObject.genericCountryBasicModelList

        dao.insertAllCountry(countryEntityList)
        val quizOptions = dao.getQuizOptionsGlobal(listOf("ES", "US"))

        for (i in 1..10) {
            assertThat(quizOptions).doesNotContain(resultCountryList.first())
            assertThat(quizOptions).doesNotContain(resultCountryList.last())
        }
    }

    @Test
    fun getQuizOptionsByRegionWithEmptyCorrectOptionsList_returnsCountriesFromCountryTable() =
        runTest {
            val countryEntityList = WorldExplorerMotherObject.genericCountryEntityList
            val europeanCountries = WorldExplorerMotherObject.europeanCountriesBasicModelList

            dao.insertAllCountry(countryEntityList)
            val quizOptions = dao.getQuizOptionsByRegion(emptyList(), "Europe")

            quizOptions.forEach {
                assertThat(europeanCountries).contains(it)
            }
        }

    @Test
    fun getQuizOptionsByRegionWithEmptyCorrectOptionsList_returns4countries() = runTest {
        val countryEntityList = WorldExplorerMotherObject.genericCountryEntityList

        dao.insertAllCountry(countryEntityList)
        val quizOptions = dao.getQuizOptionsByRegion(emptyList(), "Europe")

        assertThat(quizOptions.size).isEqualTo(4)
    }

    @Test
    fun getQuizOptionsByRegionWithCorrectOptionsList_returnsDifferentThanCorrectOptions() =
        runTest {
            val countryEntityList = WorldExplorerMotherObject.genericCountryEntityList
            val resultCountryList = WorldExplorerMotherObject.genericCountryBasicModelList

            dao.insertAllCountry(countryEntityList)
            val quizOptions = dao.getQuizOptionsByRegion(listOf("ES", "PT"), "Europe")

            for (i in 1..10) {
                assertThat(quizOptions).doesNotContain(resultCountryList.first())
                assertThat(quizOptions).doesNotContain(resultCountryList[1])
            }
        }

    @Test
    fun getRandomCca2_returnsExistingCountry() = runTest {
        val countryEntityList = WorldExplorerMotherObject.genericCountryEntityList
        val resultCountryList = WorldExplorerMotherObject.genericCountryBasicModelList

        dao.insertAllCountry(countryEntityList)
        val randomCountry = dao.getRandomCca2()

        assertThat(resultCountryList).contains(randomCountry)
    }
}
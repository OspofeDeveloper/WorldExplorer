package com.example.worldexplorer.data.network.response

import com.example.worldexplorer.motherobject.WorldExplorerMotherObject
import org.junit.Test
import org.junit.jupiter.api.Assertions.*

class WorldExplorerResponseTest {

    @Test
    fun `toCountryEntity should return a correct CountryEntity`() {
        //Given
        val worldExplorerResponse = WorldExplorerMotherObject.genericResponse

        //When
        val countryEntity = worldExplorerResponse.toCountryEntity()

        //Then
        assertEquals("ES", countryEntity.cca2)
        assertEquals("Spain", countryEntity.name)
        assertEquals(505992.0, countryEntity.area)
        assertEquals("Madrid", countryEntity.capital)
        assertEquals("ESP", countryEntity.cca3)
        assertEquals("Europe", countryEntity.continents)
        assertEquals(47351567, countryEntity.population)
        assertEquals("Europe", countryEntity.region)
    }

    @Test
    fun `toCountryEntity should return a correct CountryEntity with null fields when WorldExplorerResponse has null fields`() {
        val worldExplorerResponse = WorldExplorerMotherObject.nullResponse

        val countryEntity = worldExplorerResponse.toCountryEntity()

        assertEquals(null, countryEntity.area)
        assertEquals(null, countryEntity.capital)
        assertEquals(null, countryEntity.continents)
        assertEquals(null, countryEntity.population)
        assertEquals(null, countryEntity.region)
    }

    @Test
    fun `toCountryEntity should return a correct CountryEntity when WorldExplorerResponse has several elements in fields that are lists`() {
        val worldExplorerResponse = WorldExplorerMotherObject.bigListsResponse

        val countryEntity = worldExplorerResponse.toCountryEntity()

        assertEquals("Madrid, Barcelona, Seville", countryEntity.capital)
        assertEquals("Europe, Asia, Oceania", countryEntity.continents)
    }
}
package com.example.worldexplorer.data

import com.example.worldexplorer.data.database.entities.BorderEntity
import com.example.worldexplorer.data.database.entities.CountryEntity
import com.example.worldexplorer.data.database.entities.relations.CountryDetailBorderCrossRef
import com.example.worldexplorer.domain.RestCountriesRepository
import com.example.worldexplorer.domain.models.countries.CountryBasicModel
import com.example.worldexplorer.domain.models.detailcountries.CountryDetailModel
import com.example.worldexplorer.domain.models.detailquiz.QuizDetailModel
import com.example.worldexplorer.domain.models.travel.TravelModel
import com.example.worldexplorer.motherobject.WorldExplorerMotherObject

/**
 * Aqui haremos un fake test double con el Repository. El objetivo del repositorio es recoger los
 * datos necesarios para pasarselos a los casos de uso, por lo tanto para hacer los tests no hace
 * falta que creemos una base de datos para recuperar datos desde ahi, ni hacer una llamada real
 * a la API, solamente necesitamos simular que hemos recibidos los datos necesarios.
 *
 * El objetivo no es testear el repositorio, sino crear un repositorio que simule el comportamiento
 * que tendrá el repositorio real y que usaremos para testear el viewmodel
 *
 * Para simular la base de datos usaremos una lista donde guardamos los valores que insertamos a BD
 */
class FakeRestCountriesRepository : RestCountriesRepository {
    private var shouldReturnNetworkOk = true

    override suspend fun initWorldExplorerDatabase(): Boolean {
        if (shouldReturnNetworkOk) {
            return true
        }
        return false
    }

    override suspend fun getCountryBasic(): List<CountryBasicModel> {
        return WorldExplorerMotherObject.genericCountryBasicModelList
    }

    override suspend fun getDetailCountries(cca2: String): CountryDetailModel {
        return WorldExplorerMotherObject.countryDetailModel
    }

    override suspend fun getQuizOptionsGlobal(correctCca2List: List<String>): QuizDetailModel {
        return WorldExplorerMotherObject.quizDetailModel
    }

    override suspend fun getQuizOptionsByRegion(
        correctCca2List: List<String>,
        region: String
    ): QuizDetailModel {
        return WorldExplorerMotherObject.quizDetailModel
    }

    override suspend fun convertToQuizDetailModel(result: List<CountryEntity>): QuizDetailModel {
        return WorldExplorerMotherObject.quizDetailModel
    }

    override suspend fun getRandomCountryNameCca2(): TravelModel {
        return WorldExplorerMotherObject.travelModel
    }


}

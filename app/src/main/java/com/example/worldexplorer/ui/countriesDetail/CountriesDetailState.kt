package com.example.worldexplorer.ui.countriesDetail

/*
    En Loading definimos data object y en Error y Success un data class poque a las clases le
    pasamos parametros
 */
sealed class CountriesDetailState {
    data object Loading:CountriesDetailState()
    data class Error(val error: String):CountriesDetailState()
    data class Success(val data: String):CountriesDetailState()
}
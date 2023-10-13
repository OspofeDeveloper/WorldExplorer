package com.example.worldexplorer.ui.detailcountries

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worldexplorer.domain.usecases.detailcountries.GetDetailCountriesUseCase
import com.example.worldexplorer.ui.countries.CountriesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CountriesDetailViewModel @Inject constructor(
    private val getDetailCountriesUseCase: GetDetailCountriesUseCase
) : ViewModel() {

    private var _state = MutableStateFlow<CountriesDetailState>(CountriesDetailState.Loading)
    val state:StateFlow<CountriesDetailState> = _state

    fun getCountriesDetail(countryName: String) {
        viewModelScope.launch {
            _state.value = CountriesDetailState.Loading

            val result = withContext(Dispatchers.IO) {
                getDetailCountriesUseCase(countryName)
            }

            if (result != null) {
                _state.value = CountriesDetailState.Success(result)
            } else {
                _state.value = CountriesDetailState.Error("Ha ocurrido un error, intentelo mas tarde")
                Log.i("detail", "Resultado: $result")
            }
        }
    }
}
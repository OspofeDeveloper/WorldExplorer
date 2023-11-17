package com.example.worldexplorer.ui.detailcountries

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worldexplorer.domain.models.countries.CountriesModel
import com.example.worldexplorer.domain.usecases.detailcountries.GetDetailCountriesUseCase
import com.example.worldexplorer.util.Resource
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

    private var _state = MutableStateFlow<Resource<CountriesModel>>(Resource.Loading())
    val state:StateFlow<Resource<CountriesModel>> = _state

    fun getCountriesDetail(countryName: String) {
        viewModelScope.launch {
            _state.value = Resource.Loading()

            val result = withContext(Dispatchers.IO) {
                getDetailCountriesUseCase(countryName)
            }

            if (result != null) {
                _state.value = Resource.Success(result)
            } else {
                _state.value =
                    Resource.Error("Ha ocurrido un error, intentelo mas tarde")
                Log.i("detail", "Resultado: $result")
            }
        }
    }
}
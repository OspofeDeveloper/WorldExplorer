package com.example.worldexplorer.ui.travel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worldexplorer.domain.models.countries.CountryBasicModel
import com.example.worldexplorer.domain.models.travel.TravelModel
import com.example.worldexplorer.domain.usecases.travel.GetRandomCca2UseCase
import com.example.worldexplorer.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class TravelViewModel @Inject constructor(
    private val getRandomCca2UseCase: GetRandomCca2UseCase,
) : ViewModel() {

    private var _state = MutableStateFlow<Resource<TravelModel>>(Resource.Loading())
    val state: StateFlow<Resource<TravelModel>> = _state

    fun getRandomCountry() {
        viewModelScope.launch {
            _state.value = Resource.Loading()

            val result = getRandomCca2()

            if (result != null) {
                _state.value = Resource.Success(result)
            } else {
                _state.value = Resource.Error("Ha ocurrido un error, intentelo mas tarde")
            }
        }
    }

    private suspend fun getRandomCca2(): TravelModel {
        return withContext(Dispatchers.IO) {
            getRandomCca2UseCase()
        }
    }

    fun restartViewModel() {
        _state.value = Resource.Loading()
    }
}
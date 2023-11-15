package com.example.worldexplorer.ui.travel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worldexplorer.domain.usecases.travel.GetRandomCca2UseCase
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

    private var _state = MutableStateFlow<TravelState>(TravelState.Loading)
    val state: StateFlow<TravelState> = _state

    fun getRandomCountry() {
        viewModelScope.launch {
            _state.value = TravelState.Loading

            val result = withContext(Dispatchers.IO) {
                getRandomCca2UseCase()
            }

            if (result != null) {
                _state.value = TravelState.Success(result)
            } else {
                _state.value =
                    TravelState.Error("Ha ocurrido un error, intentelo mas tarde")
            }
        }
    }

    fun restartViewModel() {
        _state.value = TravelState.Loading
    }
}
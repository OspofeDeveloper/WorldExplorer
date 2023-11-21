package com.example.worldexplorer.ui.countries

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worldexplorer.domain.models.countries.CountryBasicModel
import com.example.worldexplorer.domain.usecases.countries.GetAllCountriesUseCase
import com.example.worldexplorer.domain.usecases.countries.InitDataFromApiUseCase
import com.example.worldexplorer.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CountriesViewModel @Inject constructor(
    private val initDataFromApiUseCase: InitDataFromApiUseCase,
    private val getAllCountriesUseCase: GetAllCountriesUseCase
) : ViewModel() {

    private var _state = MutableStateFlow<Resource<List<CountryBasicModel>>>(Resource.Loading())
    val state: StateFlow<Resource<List<CountryBasicModel>>> = _state

    private var orderedAscCountries: List<CountryBasicModel> = listOf()
    private var orderedDescCountries: List<CountryBasicModel> = listOf()


    /**
    Los Scopes nos permiten que el ciclo de vida de nuestra corrutina se adhiera al ciclo de
    vida del elemento que queremos, en este caso el viewModel. Si queremos ejecuutar una corrutina
    tiene que ir dentro del Scope.

    Y cuando usamos el Dispatchers.IO lo que queremos es que lo que esta dentro este se ejecute
    en un hilo secundario. Cualquier cosa que modifique la UI se hace en el hilo principal, pero
    si imlementamos funcionalidades externas como recoger datos externos eso se hace en el hilo
    secundario. En este caso lo que queremos que se ejecute en ese hilo secundario es la llamada al
    caso de uso ya que este recoge datos externos, por eso lo llamamos dentro de Dispatchers.IO (a
    demas hay diferentes tipos de Dispatchers pero el que se usa para operaciones de
    lectura/escritura de datos desde o hacia disco es ese)
     */
    init {
        viewModelScope.launch {
            _state.value = Resource.Loading()

            val result = withContext(Dispatchers.IO) {
                initDataFromApiUseCase()
            }

            if (result) {
                getAllCountries()
            } else {
                _state.value = Resource.Error("Ha ocurrido un error, intentelo mas tarde")
            }
        }
    }

    private fun getAllCountries() {
        viewModelScope.launch {

            val result = withContext(Dispatchers.IO) {
                getAllCountriesUseCase()
            }

            if (result.isNotEmpty()) {
                orderedAscCountries = result.sortedBy { it.name }
                orderedDescCountries = result.sortedByDescending { it.name }
                _state.value = Resource.Success(result)
            } else {
                _state.value = Resource.Error("Ha ocurrido un error, intentelo mas tarde")
            }
        }
    }

    fun getAllCountriesOrdered(position: Int) {
        when (position) {
            0 -> _state.value = Resource.Success(orderedAscCountries)
            else -> _state.value = Resource.Success(orderedDescCountries)
        }
    }


}
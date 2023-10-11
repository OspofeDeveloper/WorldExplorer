package com.example.worldexplorer.ui.countries

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worldexplorer.domain.countries.usecases.GetAllCountriesOrderAscUseCase
import com.example.worldexplorer.domain.countries.usecases.GetAllCountriesOrderDescUseCase
import com.example.worldexplorer.domain.countries.usecases.GetAllCountriesUseCase
import com.example.worldexplorer.domain.countries.model.CountriesInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountriesViewModel @Inject constructor(
    private val getAllCountriesUseCase: GetAllCountriesUseCase,
    private val getAllCountriesOrderAscUseCase: GetAllCountriesOrderAscUseCase,
    private val getAllCountriesOrderDescUseCase: GetAllCountriesOrderDescUseCase
) : ViewModel() {

    private var _countriesState = MutableStateFlow<List<CountriesInfo>>(emptyList())
    val countriesState: StateFlow<List<CountriesInfo>> = _countriesState

    init {
        viewModelScope.launch {
            val result = getAllCountriesUseCase("name,cca2")

            if (!result.isNullOrEmpty()) {
                _countriesState.value = result
            }
        }
    }

    fun getAllCountriesOrdered(position: Int) {
        viewModelScope.launch {
            val result: List<CountriesInfo> = when (position) {
                0 -> getAllCountriesOrderAscUseCase()
                else -> getAllCountriesOrderDescUseCase()
            }

            _countriesState.value = result
        }
    }
}
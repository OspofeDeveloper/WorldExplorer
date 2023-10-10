package com.example.worldexplorer.ui.listCountriesScreen.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worldexplorer.domain.GetAllCountriesOrderAscUseCase
import com.example.worldexplorer.domain.GetAllCountriesOrderDescUseCase
import com.example.worldexplorer.domain.GetAllCountriesUseCase
import com.example.worldexplorer.domain.model.CountryItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountryViewModel @Inject constructor(
    private val getAllCountriesUseCase: GetAllCountriesUseCase,
    private val getAllCountriesOrderAscUseCase: GetAllCountriesOrderAscUseCase,
    private val getAllCountriesOrderDescUseCase: GetAllCountriesOrderDescUseCase
) : ViewModel() {

    val countryItemLiveData = MutableLiveData<List<CountryItem>>()
    fun onCreate() {
        viewModelScope.launch {
            val result = getAllCountriesUseCase("name,cca2")

            if (!result.isNullOrEmpty()) {
                countryItemLiveData.postValue(result)
            }
        }
    }

    fun getAllCountriesOrdered(position: Int) {
        viewModelScope.launch {
            val result: List<CountryItem> = when (position) {
                0 -> getAllCountriesOrderAscUseCase()
                else -> getAllCountriesOrderDescUseCase()
            }

            countryItemLiveData.postValue(result)
        }
    }
}
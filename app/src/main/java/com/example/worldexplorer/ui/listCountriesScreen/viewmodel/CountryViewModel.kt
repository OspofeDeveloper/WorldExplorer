package com.example.worldexplorer.ui.listCountriesScreen.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worldexplorer.domain.GetAllCountriesUseCase
import com.example.worldexplorer.domain.model.CountryItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountryViewModel @Inject constructor(
    private val getAllCountriesUseCase: GetAllCountriesUseCase
) : ViewModel() {

    val countryItemLiveData = MutableLiveData<List<CountryItem>>()
    fun onCreate() {
        viewModelScope.launch {
            val result = getAllCountriesUseCase()

            if (!result.isNullOrEmpty()) {
                countryItemLiveData.postValue(result)
            }
        }
    }
}
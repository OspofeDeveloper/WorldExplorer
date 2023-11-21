package com.example.worldexplorer.ui.detailcountries

import android.graphics.drawable.GradientDrawable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worldexplorer.core.bitmapconverter.BitmapConverter
import com.example.worldexplorer.core.paletteutils.PaletteUtils
import com.example.worldexplorer.domain.models.detailcountries.CountryDetailModel
import com.example.worldexplorer.domain.usecases.detailcountries.GetDetailCountriesUseCase
import com.example.worldexplorer.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class CountriesDetailViewModel @Inject constructor(
    private val getDetailCountriesUseCase: GetDetailCountriesUseCase,
    private val bitmapConverter: BitmapConverter,
    @Named("paletteUtilsCountryDetail") private val paletteUtils: PaletteUtils
) : ViewModel() {

    private var _state = MutableStateFlow<Resource<CountryDetailModel>>(Resource.Loading())
    val state: StateFlow<Resource<CountryDetailModel>> = _state

    private var _backGroundColor = MutableStateFlow(GradientDrawable())
    val backGroundColor: StateFlow<GradientDrawable> = _backGroundColor

    fun getCountriesDetail(cca2: String) {
        viewModelScope.launch {
            _state.value = Resource.Loading()

            val result = withContext(Dispatchers.IO) {
                getDetailCountriesUseCase(cca2)
            }

            if (result != null) {
                val bitmap = bitmapConverter.getBitmapFromUrl(result.imageUrl)
                _backGroundColor.value = paletteUtils.getBackgroundGradient(bitmap)
                _state.value = Resource.Success(result)
            } else {
                _state.value =
                    Resource.Error("Ha ocurrido un error, intentelo mas tarde")
            }
        }
    }
}
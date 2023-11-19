package com.example.worldexplorer.ui.detailquiz

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worldexplorer.domain.models.detailquiz.QuizDetailModel
import com.example.worldexplorer.domain.usecases.quiz.GetQuizOptionsByRegionUseCase
import com.example.worldexplorer.domain.usecases.quiz.GetQuizOptionsGlobalUseCase
import com.example.worldexplorer.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class QuizDetailViewModel @Inject constructor(
    private val getQuizOptionsGlobalUseCase: GetQuizOptionsGlobalUseCase,
    private val getQuizOptionsByRegionUseCase: GetQuizOptionsByRegionUseCase
) : ViewModel() {

    private var correctCca2List = mutableListOf<String>()

    private var _state = MutableStateFlow<Resource<QuizDetailModel>>(Resource.Loading())
    val state: StateFlow<Resource<QuizDetailModel>> = _state

    fun getQuizInformation(region: String) {
        viewModelScope.launch {
            _state.value = Resource.Loading()

            val result = withContext(Dispatchers.IO) {
                getQuizOptionsGlobalUseCase(correctCca2List)
            }

            if (result != null) {
                Log.d("Pozo", "correctCca2: $correctCca2List")
                correctCca2List.add(result.cca2)
                _state.value = Resource.Success(result)
            } else {
                _state.value = Resource.Error("Ha ocurrido un error, intentelo mas tarde")
            }
        }
    }

    fun restartListCorrectAnswers() {
        correctCca2List.clear()
    }
}
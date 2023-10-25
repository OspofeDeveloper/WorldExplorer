package com.example.worldexplorer.ui.detailquiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worldexplorer.domain.models.countries.CountriesModel
import com.example.worldexplorer.domain.usecases.countries.GetAllCountriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class QuizDetailViewModel @Inject constructor(
    private val getAllCountriesUseCase: GetAllCountriesUseCase
) : ViewModel() {

    private var _state = MutableStateFlow<QuizDetailState>(QuizDetailState.Loading)
    val state: StateFlow<QuizDetailState> = _state

    private var correctOptionsId = mutableListOf<Int>()

    fun getQuizInformation() {
        viewModelScope.launch {
            _state.value = QuizDetailState.Loading

            val result = withContext(Dispatchers.IO) {
                getAllCountriesUseCase()
            }

            if (result.isNotEmpty()) {
                val optionsId = getRandomOptionsIdList(result.size)
                val quizOptions = getQuizOptionsList(result, optionsId)
                _state.value = QuizDetailState.Success(quizOptions)
            } else {
                _state.value = QuizDetailState.Error("Ha ocurrido un error, intentelo mas tarde")
            }
        }
    }

    private fun getRandomOptionsIdList(size: Int): List<List<Int>> {
        var randomOptionsList = mutableListOf<List<Int>>()

        for (i in 1..10) {
            randomOptionsList.add(getRandomOptionsId(size))
        }
        return randomOptionsList
    }

    private fun getRandomOptionsId(size: Int): List<Int> {
        var options = mutableListOf<Int>()

        for (i in 1..4) {
            var randomId = Random.nextInt(1, size)

            while (options.contains(randomId) || correctOptionsId.contains(randomId)) {
                randomId = Random.nextInt(1, size)
            }
            options.add(randomId)
        }

        correctOptionsId.add(options[0])

        return options
    }

    private fun getQuizOptionsList(
        result: List<CountriesModel>,
        optionsId: List<List<Int>>
    ): List<Pair<String, List<Pair<String, Boolean>>>> {
        return optionsId.map { getQuizOptions(result, it) }
    }

    private fun getQuizOptions(
        result: List<CountriesModel>,
        optionsId: List<Int>
    ): Pair<String, List<Pair<String, Boolean>>> {
        lateinit var finalOptions: Pair<String, List<Pair<String, Boolean>>>
        var optionNames = mutableListOf<Pair<String, Boolean>>()

        optionNames.add(result[optionsId[0]].name to true)

        for (i in 1 until optionsId.size) {
            optionNames.add(result[optionsId[i]].name to false)
        }

        finalOptions = result[optionsId[0]].cca2.lowercase() to optionNames.shuffled()
        return finalOptions
    }
}
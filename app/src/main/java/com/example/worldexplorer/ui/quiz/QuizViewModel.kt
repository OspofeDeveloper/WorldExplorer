package com.example.worldexplorer.ui.quiz

import androidx.lifecycle.ViewModel
import com.example.worldexplorer.data.providers.QuizProvider
import com.example.worldexplorer.domain.model.QuizInfo
import com.example.worldexplorer.domain.model.QuizInfo.Africa
import com.example.worldexplorer.domain.model.QuizInfo.Asia
import com.example.worldexplorer.domain.model.QuizInfo.Europe
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

/*
  En el caso de este ViewModel no hacemos uso de un Caso de Uso ya que lo unico que tenemos que
  hacer con esos datos es cargarlos al principio, y ya no nos comunicamos mas con la capa de data.
  Esto no quiere decir que esté mal recuperar los datos con un UseCase, y un Repository, pero como
  es tan simple nos lo podemos ahorrar.

  Otra curiosidad es que si usamos una clase que inyectamos solamente en variables o el init como
  en este caso, no hace falta declararo como "private val quizProvider", solamente hace falta
  meterlo en una variable si lo usamos dentro de una funcion del viewmodel
*/
@HiltViewModel
class QuizViewModel @Inject constructor(quizProvider: QuizProvider) : ViewModel() {

    private var _quizState = MutableStateFlow<List<QuizInfo>>(emptyList())
    val quizState: StateFlow<List<QuizInfo>> = _quizState

    init {
        _quizState.value = quizProvider.getQuizItems()
    }
}
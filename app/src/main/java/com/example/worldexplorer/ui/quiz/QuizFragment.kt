package com.example.worldexplorer.ui.quiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.worldexplorer.databinding.FragmentQuizBinding
import com.example.worldexplorer.ui.quiz.adapter.QuizAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class QuizFragment : Fragment() {

    private val quizViewModel by viewModels<QuizViewModel>()
    private lateinit var quizAdapter: QuizAdapter

    private var _binding: FragmentQuizBinding? = null
    private val binding get() = _binding!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        initRecyclerView()
        initUIState()
    }

    //En vez de poner dos veces binding.rvQuiz al hacer "binding.rvQuiz.layoutManager = LinearLayoutManager(context)
    //y binding.rvQuiz.adapter = QuizAdapter()" usamos ".apply"
    private fun initRecyclerView() {
        quizAdapter = QuizAdapter()

        binding.rvQuiz.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = quizAdapter
        }
    }

    //Siempre que lanzamos corrutinas en fragment lo hacemos con lifecycleScope ya que asi la corrutina se adhiere
    //al ciclo de vida del Fragment. Con "repeatOnLifecycle(Lifecycle.State.STARTED" indicamos que se suscriba al viewmodel
    //cuando empiece el ciclo de vida
    private fun initUIState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                quizViewModel.quizState.collect {
                    //Cambios en Quiz
                    quizAdapter.updateList(it)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuizBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}
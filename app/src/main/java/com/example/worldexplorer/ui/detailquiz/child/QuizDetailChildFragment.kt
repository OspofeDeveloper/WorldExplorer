package com.example.worldexplorer.ui.detailquiz.child

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil.load
import com.example.worldexplorer.databinding.FragmentQuizDetailChildBinding
import com.example.worldexplorer.ui.detailquiz.child.adapter.QuizDetailAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

const val QUESTION_INDEX = "0"

@AndroidEntryPoint
class QuizDetailChildFragment : Fragment() {
    private var _binding: FragmentQuizDetailChildBinding? = null
    private val binding get() = _binding!!
    private val viewModel: QuizDetailViewModel by activityViewModels()
    private var questionIndex: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            questionIndex = it.getString(QUESTION_INDEX)?.toInt() ?: 0
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuizDetailChildBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        initUIState()
    }

    private fun initUIState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect {
                    when (it) {
                        is QuizDetailState.Error -> errorState(it.error)
                        QuizDetailState.Loading -> loadState()
                        is QuizDetailState.Success -> successState(it)
                    }
                }
            }
        }
    }

    private fun loadState() {}

    private fun errorState(error: String) = Toast.makeText(context, error, Toast.LENGTH_LONG).show()

    private fun successState(state: QuizDetailState.Success) {
        binding.ivFlagQuiz.load("https://flagcdn.com/w320/${state.quizOptions[questionIndex!!].first}.png")
        initGridView(state.quizOptions[questionIndex!!].second)
    }

    private fun initGridView(quizOptions: List<Pair<String, Boolean>>) {
        binding.grid.adapter = QuizDetailAdapter(
            requireContext(),
            quizOptions,
            onItemSelected = {result ->
                setFragmentResult("requestKey", bundleOf("bundleKey" to result))
            }
        )
    }
}
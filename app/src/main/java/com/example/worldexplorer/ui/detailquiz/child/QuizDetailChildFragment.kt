package com.example.worldexplorer.ui.detailquiz.child

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil.load
import com.example.worldexplorer.databinding.FragmentQuizDetailChildBinding
import com.example.worldexplorer.ui.detailquiz.QuizDetailViewModel
import com.example.worldexplorer.ui.detailquiz.child.adapter.QuizDetailAdapter
import com.example.worldexplorer.util.Constants.QUESTION_INDEX
import com.example.worldexplorer.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

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
                        is Resource.Error -> errorState(it.error)
                        is Resource.Loading -> loadState()
                        is Resource.Success -> successState(it.data)
                    }
                }
            }
        }
    }

    private fun loadState() {}

    private fun errorState(error: String) = Toast.makeText(context, error, Toast.LENGTH_LONG).show()

    private fun successState(quizOptions: List<Pair<String, List<Pair<String, Boolean>>>>) {
        binding.ivFlagQuiz.load("https://flagcdn.com/w320/${quizOptions[questionIndex!!].first}.png")
        initGridView(quizOptions[questionIndex!!].second)
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
package com.example.worldexplorer.ui.detailquiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.worldexplorer.databinding.FragmentQuizDetailBinding
import com.example.worldexplorer.ui.detailquiz.adapter.QuizDetailAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class QuizDetailFragment : Fragment() {

    private var _binding: FragmentQuizDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: QuizDetailViewModel by viewModels()
    private val args: QuizDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentQuizDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getQuizInformation()
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
                viewModel.state.collect{
                    when(it) {
                        is QuizDetailState.Error -> errorState(it.error)
                        QuizDetailState.Loading -> loadState()
                        is QuizDetailState.Success -> successState(it)
                    }
                }
            }
        }
    }

    private fun loadState() {
        binding.pbQuizDetail.isVisible = true
    }

    private fun errorState(error: String) {
        binding.pbQuizDetail.isVisible = false
        Toast.makeText(context, error, Toast.LENGTH_LONG).show()
    }

    private fun successState(state: QuizDetailState.Success) {
        binding.pbQuizDetail.isVisible = false
        binding.ivFlagQuiz.load("https://flagcdn.com/w320/${state.quizOptions.first}.png")
        initGridView(state.quizOptions)
    }

    private fun initGridView(quizOptions: Pair<String, List<Pair<String, String>>>) {
        binding.grid.adapter = QuizDetailAdapter(requireContext(), quizOptions)
    }
}
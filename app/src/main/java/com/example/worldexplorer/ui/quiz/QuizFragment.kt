package com.example.worldexplorer.ui.quiz

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
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

    private fun initRecyclerView() {
        quizAdapter = QuizAdapter(
            onItemSelected = {
                findNavController().navigate(
                    QuizFragmentDirections.actionQuizFragmentToQuizDetailFragment(it.name)
                )
            }
        )

        binding.rvQuiz.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = quizAdapter
        }
    }

    private fun initUIState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                quizViewModel.quiz.collect {
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
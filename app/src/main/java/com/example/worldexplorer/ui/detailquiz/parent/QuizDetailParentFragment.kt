package com.example.worldexplorer.ui.detailquiz.parent

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.worldexplorer.R
import com.example.worldexplorer.databinding.FragmentQuizDetailChildBinding
import com.example.worldexplorer.databinding.FragmentQuizDetailParentBinding
import com.example.worldexplorer.ui.detailquiz.child.FLAG_CODE
import com.example.worldexplorer.ui.detailquiz.child.OPTIONS
import com.example.worldexplorer.ui.detailquiz.child.QuizDetailChildFragment
import com.example.worldexplorer.ui.detailquiz.child.adapter.QuizDetailAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class QuizDetailParentFragment : Fragment() {

    private var _binding: FragmentQuizDetailParentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: QuizDetailViewModel by viewModels()
    private val args: QuizDetailParentFragmentArgs by navArgs()
    private var questionNumber = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentQuizDetailParentBinding.inflate(inflater, container, false)
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
        initListeners()
        initUIState()
    }

    private fun initListeners() {
        /** Set the listener on the child fragmentManager.*/
        childFragmentManager.setFragmentResultListener("requestKey", viewLifecycleOwner) { _, bundle ->
            val result = bundle.getBoolean("bundleKey")
            Log.d("Oscar", "result: $result")
            questionNumber++
            initUIState()
        }
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

    private fun loadState() {
        binding.pbQuizDetail.isVisible = true
    }

    private fun errorState(error: String) {
        binding.pbQuizDetail.isVisible = false
        Toast.makeText(context, error, Toast.LENGTH_LONG).show()
    }

    private fun successState(state: QuizDetailState.Success) {
        binding.pbQuizDetail.isVisible = false

        /** Creamos el bundle con los valores que le pasamos al fragmnto hijo*/
        val bundle = bundleOf(
            FLAG_CODE to state.quizOptions[questionNumber].first,
            OPTIONS to state.quizOptions[questionNumber].second
        )
        Log.d("Oscar", "Iteracion numero $questionNumber")

        /** Creamos el fragmento hijo con los datos que queremos */
        childFragmentManager.commit {
            setReorderingAllowed(true)
            replace<QuizDetailChildFragment>(R.id.fragmentContainerView, args = bundle)
        }
    }
}
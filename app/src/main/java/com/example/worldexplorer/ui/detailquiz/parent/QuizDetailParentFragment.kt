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
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.worldexplorer.R
import com.example.worldexplorer.databinding.FragmentQuizDetailParentBinding
import com.example.worldexplorer.ui.detailquiz.child.QUESTION_INDEX
import com.example.worldexplorer.ui.detailquiz.child.QuizDetailChildFragment
import com.example.worldexplorer.ui.detailquiz.child.QuizDetailState
import com.example.worldexplorer.ui.detailquiz.child.QuizDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class QuizDetailParentFragment : Fragment() {

    private var _binding: FragmentQuizDetailParentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: QuizDetailViewModel by activityViewModels()
    private val args: QuizDetailParentFragmentArgs by navArgs()
    private var questionNumber: Int = 0
    private var currentProgress: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getQuizInformation()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentQuizDetailParentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        initListeners()
        initUIState()
    }

    private fun initUIState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect {
                    when (it) {
                        is QuizDetailState.Error -> errorState(it.error)
                        QuizDetailState.Loading -> loadState()
                        is QuizDetailState.Success -> successState()
                    }
                }
            }
        }
    }

    private fun initListeners() {
        /** Set the listener on the child fragmentManager.*/
        childFragmentManager.setFragmentResultListener("requestKey", viewLifecycleOwner
        ) { _, bundle ->
            val result = bundle.getBoolean("bundleKey")
            questionNumber++
            updateProgressBar()
            initChildFragment()
        }
    }

    private fun updateProgressBar() {
        currentProgress += 10
        binding.pbLinearProgress.progress = currentProgress
    }

    private fun loadState() {
        binding.pbQuizDetail.isVisible = true
    }

    private fun errorState(error: String) {
        binding.pbQuizDetail.isVisible = false
        Toast.makeText(context, error, Toast.LENGTH_LONG).show()
    }

    private fun successState() {
        binding.pbQuizDetail.isVisible = false
        initProgressBar()
        initChildFragment()
    }

    private fun initProgressBar() {
        binding.pbLinearProgress.max = 100
    }

    private fun initChildFragment() {
        /** Creamos el bundle con los valores que le pasamos al fragmnto hijo*/
        val bundle = bundleOf(
            QUESTION_INDEX to questionNumber.toString()
        )

        /** Creamos el fragmento hijo con los datos que queremos */
        childFragmentManager.commit {
            setReorderingAllowed(true)
            replace<QuizDetailChildFragment>(R.id.fragmentContainerView, args = bundle)
        }
    }
}
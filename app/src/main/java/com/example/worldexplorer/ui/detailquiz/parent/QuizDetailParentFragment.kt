package com.example.worldexplorer.ui.detailquiz.parent

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.navigation.fragment.navArgs
import com.example.worldexplorer.R
import com.example.worldexplorer.databinding.FragmentQuizDetailParentBinding
import com.example.worldexplorer.ui.detailquiz.QuizDetailViewModel
import com.example.worldexplorer.ui.detailquiz.child.QuizDetailChildFragment
import com.example.worldexplorer.util.Constants.QUESTION_INDEX
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuizDetailParentFragment : Fragment() {

    private var _binding: FragmentQuizDetailParentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: QuizDetailViewModel by activityViewModels()
    private val args: QuizDetailParentFragmentArgs by navArgs()

    private var correctanswers = 0
    private var questionNumber: Int = 0
    private var currentProgress: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuizDetailParentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    override fun onDestroy() {
        super.onDestroy()
        restartResult()
        viewModel.restartListCorrectAnswers()
    }

    private fun restartResult() {
        binding.pbResult.progress = 0
    }

    private fun initUI() {
        initChildFragment()
        initListeners()
    }

    private fun initChildFragment() {
        viewModel.getQuizInformation(getString(args.region))

        val bundle = bundleOf(
            QUESTION_INDEX to questionNumber.toString()
        )

        childFragmentManager.commit {
            setReorderingAllowed(true)
            replace<QuizDetailChildFragment>(R.id.fragmentContainerView, args = bundle)
        }
    }

    private fun initListeners() {
        initBackButtonListener()
        initChildFragmentListener()
    }

    private fun initBackButtonListener() {
        binding.btBack.setOnClickListener { requireActivity().onBackPressed() }
    }

    private fun initChildFragmentListener() {
        childFragmentManager.setFragmentResultListener(
            "requestKey", viewLifecycleOwner
        ) { _, bundle ->
            val result = bundle.getBoolean("bundleKey")
            questionNumber++
            if (result) correctanswers++

            if (questionNumber < 10) {
                updateProgressBar()
                initChildFragment()
            } else {
                binding.apply {
                    pbLinearProgress.isVisible = false
                    fragmentContainerView.isVisible = false
                    tvQuestion.isVisible = false
                }
                displayFinalAnimation()
            }
        }
    }

    private fun updateProgressBar() {
        currentProgress += 10
        binding.pbLinearProgress.progress = currentProgress
    }

    private fun displayFinalAnimation() {
        binding.apply {
            finalLottieQuiz.isVisible = true
            tvFinalResult.isVisible = true
            tvScore.text = getString(R.string.final_results_score, correctanswers.toString())
            pbResult.isVisible = true
            btBack.isVisible = true

            val animator = ObjectAnimator.ofInt(pbResult, "progress", 0, correctanswers * 7)

            animator.apply {
                duration = 400
                start()
            }
        }
    }

}
package com.example.worldexplorer.ui.detailquiz.child

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import coil.load
import com.example.worldexplorer.databinding.FragmentQuizDetailChildBinding
import com.example.worldexplorer.ui.detailquiz.child.adapter.QuizDetailAdapter

const val FLAG_CODE = ""
const val OPTIONS = "options"

class QuizDetailChildFragment : Fragment() {
    private var _binding: FragmentQuizDetailChildBinding? = null
    private val binding get() = _binding!!

    private var flagCode: String? = null
    private var options: Pair<String, List<Pair<String, Boolean>>>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            flagCode = it.getString(FLAG_CODE)
            Log.i("Oscar", "${flagCode.orEmpty()}, $options")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentQuizDetailChildBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        initListeners()
    }

    private fun initListeners() {
        binding.ivFlagQuiz.setOnClickListener {
            val result = true
            setFragmentResult("requestKey", bundleOf("bundleKey" to result))
        }
    }

    private fun initView() {
        binding.ivFlagQuiz.load("https://flagcdn.com/w320/${flagCode}.png")

        val yourVariable: List<Pair<String, Boolean>> =
            listOf(
                Pair("Morocco", false),
                Pair("Lithuania", false),
                Pair("Antigua and Barbuda", false),
                Pair("Saint Vincent and the Grenadines", true)
            )

        initGridView(yourVariable)
    }

    private fun initGridView(quizOptions: List<Pair<String, Boolean>>) {
        binding.grid.adapter = QuizDetailAdapter(
            requireContext(),
            quizOptions
        )
    }
}
package com.example.worldexplorer.ui.detailquiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.example.worldexplorer.databinding.FragmentQuizDetailBinding
import com.example.worldexplorer.ui.detailquiz.adapter.QuizDetailAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class QuizDetailFragment : Fragment() {

    private var _binding: FragmentQuizDetailBinding? = null
    private val binding get() = _binding!!
    private val args: QuizDetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentQuizDetailBinding.inflate(inflater, container, false)
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
                initGridView()
            }
        }
    }

    private fun initGridView() {
        val gridView: GridView = binding.grid

        val data = listOf("Item 1", "Item 2", "Item 3", "Item 4") // Replace with your data
        val adapter = QuizDetailAdapter(requireContext(), data)

        gridView.adapter = adapter
    }
}
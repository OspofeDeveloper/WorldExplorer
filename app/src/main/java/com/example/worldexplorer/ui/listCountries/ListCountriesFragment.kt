package com.example.worldexplorer.ui.listCountries

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.worldexplorer.databinding.FragmentListCountriesBinding

class ListCountriesFragment : Fragment() {
    private var _binding: FragmentListCountriesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentListCountriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView(view)
    }

    private fun initRecyclerView(view: View) {
        binding.rvListCountries.layoutManager = GridLayoutManager(context, 2)
        binding.rvListCountries.adapter = ListCountriesAdapter(ListCountriesProvider.countryList)
    }
}
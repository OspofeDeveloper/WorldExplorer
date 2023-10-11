package com.example.worldexplorer.ui.countries

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.worldexplorer.R
import com.example.worldexplorer.databinding.FragmentListCountriesBinding
import com.example.worldexplorer.domain.countries.model.CountryItem
import com.example.worldexplorer.ui.countries.adapter.CountriesAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CountriesFragment : Fragment() {
    private val viewModel: CountriesViewModel by viewModels()
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

        setupUI()
        observeViewModel(view)
    }

    private fun observeViewModel(view: View) {
        viewModel.onCreate()

        viewModel.countryItemLiveData.observe(viewLifecycleOwner, Observer {countryList ->
            initRecyclerView(view, countryList)
        })
    }

    private fun setupUI() {

        //Setting Exposed Dropdown Menu
        val orderByItems = resources.getStringArray(R.array.order_by_dropdown_options)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.order_by_dropdown_menu_item, orderByItems)
        binding.autoCompleteText.setAdapter(arrayAdapter)

        binding.autoCompleteText.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            viewModel.getAllCountriesOrdered(position)
        }
    }

    private fun initRecyclerView(view: View, countryList: List<CountryItem>) {
        binding.rvListCountries.layoutManager = GridLayoutManager(context, 2)
        binding.rvListCountries.adapter = CountriesAdapter(countryList)
    }
}

/*
    //If we put this code in onCreateView function we have problems when returning to this fragment from navigation
    override fun onResume() {
        super.onResume()

        val orderByItems = resources.getStringArray(R.array.order_by_dropdown_options)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.order_by_dropdown_menu_item, orderByItems)
        binding.autoCompleteText.setAdapter(arrayAdapter)
    }
*/
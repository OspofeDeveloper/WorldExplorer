package com.example.worldexplorer.ui.countries

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.worldexplorer.R
import com.example.worldexplorer.databinding.FragmentCountriesBinding
import com.example.worldexplorer.domain.countries.model.CountriesInfo
import com.example.worldexplorer.ui.countries.adapter.CountriesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CountriesFragment : Fragment() {

    private val countriesViewModel: CountriesViewModel by viewModels()
    private lateinit var countriesAdapter: CountriesAdapter

    private var _binding: FragmentCountriesBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        initDropdownMenu()
        initListeners()
        initUIState()
    }

    private fun initListeners() {
        binding.autoCompleteText.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            countriesViewModel.getAllCountriesOrdered(position)
        }
    }

    private fun initDropdownMenu() {
        val orderByItems = resources.getStringArray(R.array.order_by_dropdown_options)

        val arrayAdapter =   ArrayAdapter(requireContext(), R.layout.dropdown_menu_item, orderByItems)
        binding.autoCompleteText.apply {
            setAdapter(arrayAdapter)
            setDropDownBackgroundDrawable(
                ColorDrawable(ContextCompat.getColor(context, R.color.primaryDark))
            )
        }
    }

    private fun initUIState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                countriesViewModel.state.collect {
                    initRecyclerView(it)
                }
            }
        }
    }

    private fun initRecyclerView(countryList: List<CountriesInfo>) {
        countriesAdapter = CountriesAdapter(countryList, onItemSelected = {
            findNavController().navigate(
                CountriesFragmentDirections.actionCountriesFragmentToCountriesDetailActivity(it.name)
            )
        })

        binding.rvListCountries.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = countriesAdapter
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCountriesBinding.inflate(inflater, container, false)
        return binding.root
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
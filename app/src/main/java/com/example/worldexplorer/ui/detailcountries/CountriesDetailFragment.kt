package com.example.worldexplorer.ui.detailcountries

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.worldexplorer.databinding.FragmentCountriesDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CountriesDetailFragment : Fragment() {

    private var _binding: FragmentCountriesDetailBinding? = null
    private val binding get() = _binding!!

    private val countriesDetailViewModel: CountriesDetailViewModel by viewModels()

    private val args: CountriesDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        countriesDetailViewModel.getCountriesDetail(args.cca2)
    }

    private fun initUI() {
        initListeners()
        initUIState()
    }

    private fun initListeners() {
        binding.ivBack.setOnClickListener { requireActivity().onBackPressed() }
    }

    private fun initUIState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                countriesDetailViewModel.state.collect {
                    when (it) {
                        CountriesDetailState.Loading -> loadingState()
                        is CountriesDetailState.Error -> errorState()
                        is CountriesDetailState.Success -> successState(it)
                    }
                }
            }
        }
    }

    private fun loadingState() {
        binding.pbDetailCountries.isVisible = true
    }

    private fun errorState() {
        binding.pbDetailCountries.isVisible = true

    }

    private fun successState(state: CountriesDetailState.Success) {
        binding.pbDetailCountries.isVisible = false
        binding.ivFlag.load("https://flagcdn.com/w320/${args.cca2.lowercase()}.png")

        state.detailCountry.apply {
            binding.tvCountryTitle.text = "${args.name} (${cca3})"
            binding.tvArea.text = area.toString()
            binding.tvBorders.text = borders
            binding.tvContinents.text = continents
            binding.tvCapital.text = borders
            binding.tvPopulation.text = population.toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCountriesDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

}
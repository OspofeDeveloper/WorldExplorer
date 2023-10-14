package com.example.worldexplorer.ui.detailcountries

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.navArgs
import coil.load
import com.example.worldexplorer.databinding.ActivityCountriesDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CountriesDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCountriesDetailBinding
    private val countriesDetailViewModel: CountriesDetailViewModel by viewModels()

    private val args: CountriesDetailActivityArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCountriesDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
        countriesDetailViewModel.getCountriesDetail(args.cca2)
    }

    private fun initUI() {
        initListeners()
        initUIState()
    }

    private fun initListeners() {
        binding.ivBack.setOnClickListener { onBackPressed() }
    }

    private fun initUIState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                countriesDetailViewModel.state.collect {
                    when (it) {
                        CountriesDetailState.Loading -> loadingState()
                        is CountriesDetailState.Success -> successState(it)
                    }
                }
            }
        }
    }

    private fun loadingState() {
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
}
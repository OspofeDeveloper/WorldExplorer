package com.example.worldexplorer.ui.countriesDetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.navArgs
import com.example.worldexplorer.databinding.ActivityCountriesDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
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
    }

    private fun initUI() {
        initUIState()
    }

    private fun initUIState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                countriesDetailViewModel.state.collect {
                    when (it) {
                        CountriesDetailState.Loading -> loadingState()
                        is CountriesDetailState.Error -> errorState()
                        is CountriesDetailState.Success -> successState()
                    }
                }
            }
        }
    }

    private fun loadingState() {
        binding.pbDetailCountries.isVisible = true
    }

    private fun errorState() {
        TODO("Not yet implemented")
    }

    private fun successState() {
        TODO("Not yet implemented")
    }
}
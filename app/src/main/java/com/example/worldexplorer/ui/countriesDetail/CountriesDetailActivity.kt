package com.example.worldexplorer.ui.countriesDetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.navArgs
import com.example.worldexplorer.R
import com.example.worldexplorer.databinding.ActivityCountriesDetailBinding
import com.example.worldexplorer.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CountriesDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCountriesDetailBinding
    private val countriesDetailViewModel by viewModels<CountriesDetailViewModel>()

    private val args:CountriesDetailActivityArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCountriesDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        args.countryName
    }
}
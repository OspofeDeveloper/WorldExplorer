package com.example.worldexplorer.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.worldexplorer.R
import com.example.worldexplorer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
package com.example.worldexplorer.ui.detailcountries

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.worldexplorer.R
import com.example.worldexplorer.databinding.FragmentCountriesDetailBinding
import com.example.worldexplorer.domain.models.detailcountries.CountryDetailModel
import com.example.worldexplorer.ui.detailcountries.adapter.CountriesDetailAdapter
import com.example.worldexplorer.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class CountriesDetailFragment : Fragment() {

    private var _binding: FragmentCountriesDetailBinding? = null
    private val binding get() = _binding!!
    private val countriesDetailViewModel: CountriesDetailViewModel by viewModels()
    private val args: CountriesDetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initAnimations()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCountriesDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        countriesDetailViewModel.getCountriesDetail(args.cca2)
    }

    private fun initAnimations() {
        val animation = TransitionInflater.from(context).inflateTransition(
            android.R.transition.move
        )
        sharedElementEnterTransition = animation
        postponeEnterTransition(200, TimeUnit.MILLISECONDS)
    }

    private fun initUI() {
        initView()
        initListeners()
        initUIState()
        initBackgroundColor()
    }

    private fun initView() {
        binding.ivFlag.transitionName = args.cca2
        binding.tvCountryTitle.transitionName = args.name
    }

    private fun initListeners() {
        binding.ivBack.setOnClickListener { requireActivity().onBackPressed() }
    }

    private fun initUIState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                countriesDetailViewModel.state.collect {
                    when (it) {
                        is Resource.Loading -> loadingState()
                        is Resource.Error -> errorState(it.error)
                        is Resource.Success -> successState(it.data)
                    }
                }
            }
        }
    }

    private fun initBackgroundColor() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                countriesDetailViewModel.backGroundColor.collect {
                    binding.clFragmentCountries.background = it
                }
            }
        }
    }


    private fun loadingState() {
        binding.pbDetailCountries.isVisible = true
    }

    private fun errorState(error: String) {
        binding.pbDetailCountries.isVisible = false
        Toast.makeText(context, error, Toast.LENGTH_LONG).show()
    }

    private fun successState(data: CountryDetailModel) {
        binding.pbDetailCountries.isVisible = false

        data.apply {
            binding.tvCountryTitle.text = args.name
            binding.tvContinents.text = continents
            binding.tvCapital.text = capital
            binding.ivFlag.load(imageUrl)

            initCountryStats(population, area)
            initGridView(cca2Borders, nameBorders, args.name)
        }
    }

    private fun initCountryStats(population: Int?, area: Double?) {
        setTextPopulationAnimation(population!!)
        setTextPopulationAnimation(area!!)
        setProgressbarAnimation(binding.pbPopulation)
        setProgressbarAnimation(binding.pbArea)
    }

    private fun initGridView(
        cca2Borders: List<String>,
        nameBorders: List<String>,
        country: String
    ) {
        if (cca2Borders.isEmpty() && nameBorders.isEmpty()) {
            binding.tvIslandExplanation.apply {
                isVisible = true
                text = getString(R.string.island_explanation, country)
            }
        } else {
            binding.gvListBorders.apply {
                isVisible = true
                adapter = CountriesDetailAdapter(requireContext(), nameBorders, cca2Borders)
            }
        }
    }

    private fun setProgressbarAnimation(progressbar: ProgressBar) {
        val animator = ObjectAnimator.ofInt(progressbar, "progress", 0, 100)

        animator.apply {
            duration = 1000
            start()
        }
    }

    private fun <T : Number> setTextPopulationAnimation(finalValue: T) {
        when (finalValue) {
            is Int -> {
                val animator = ValueAnimator.ofInt(0, finalValue)

                animator.addUpdateListener { animation ->
                    val value = animation.animatedValue as Int
                    binding.tvPopulation.text =
                        getString(R.string.total_population, value.toString())
                }

                animator.duration = 1000
                animator.start()
            }

            is Double -> {
                val animator = ValueAnimator.ofFloat(0f, finalValue.toFloat())

                animator.addUpdateListener { animation ->
                    val value = animation.animatedValue as Float
                    binding.tvArea.text = getString(R.string.total_area, (value / 1000).toString())
                }

                animator.duration = 1000
                animator.start()
            }

            else -> throw IllegalArgumentException("Unsupported type: ${finalValue::class.java.simpleName}")
        }
    }

}
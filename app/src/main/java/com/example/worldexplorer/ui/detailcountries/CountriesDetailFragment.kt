package com.example.worldexplorer.ui.detailcountries

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import androidx.palette.graphics.Palette
import coil.load
import com.example.worldexplorer.R
import com.example.worldexplorer.data.database.entities.BorderEntity
import com.example.worldexplorer.data.database.entities.relations.CountryDetailWithBorder
import com.example.worldexplorer.databinding.FragmentCountriesDetailBinding
import com.example.worldexplorer.ui.detailcountries.adapter.CountriesDetailAdapter
import com.example.worldexplorer.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class CountriesDetailFragment : Fragment() {

    private var borderNames: MutableList<String> = mutableListOf()
    private var borderCca2: MutableList<String> = mutableListOf()
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

    /** Configuramos una transición de entrada para elementos compartidos al ingresar al
     * fragmento y la posponemos por 200 milisegundos antes de comenzar.
     * Es útil para sincronizar y controlar la animación de entrada de elementos
     * compartidos durante la transición entre fragmentos. */
    private fun initAnimations() {
        val animation = TransitionInflater.from(context).inflateTransition(
            android.R.transition.move
        )
        sharedElementEnterTransition = animation
        postponeEnterTransition(200, TimeUnit.MILLISECONDS)
    }

    /**Lo que tenga que ver con modificar elementos de la vista aqui, porque en onViewCreated
     * es el primer momento del ciclo de vida de la actividad en que los elementos de la vista
     * ya están creados. En cambio la transición si que la podemos poner en onCreate así se
     * inicializa antes de que se cree la vista y no pasa nada */
    private fun initUI() {
        initView()
        initListeners()
        initUIState()
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
                        is Resource.Success -> successState(it.data.first())
                    }
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

    private fun successState(data: CountryDetailWithBorder) {
        binding.pbDetailCountries.isVisible = false
        setBackgroundColor()

        data.apply {
            binding.tvCountryTitle.text = args.name
            binding.tvContinents.text = countryDetail.continents
            binding.tvCapital.text = countryDetail.continents

            initCountryStats(countryDetail.population, countryDetail.area)
            initGridView(border, args.name)
        }
    }

    private fun initCountryStats(population: Int?, area: Double?) {
        setTextPopulationAnimation(population!!)
        setTextPopulationAnimation(area!!)
        setProgressbarAnimation(binding.pbPopulation)
        setProgressbarAnimation(binding.pbArea)
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


    private fun initGridView(borders: List<BorderEntity>, country: String) {
        if (borders.isEmpty()) {
            binding.tvIslandExplanation.apply {
                isVisible = true
                text = getString(R.string.island_explanation, country)
            }
        } else {
            borders.forEach {
                borderCca2.add(it.cca2)
                borderNames.add(it.name)
            }
            binding.gvListBorders.apply {
                isVisible = true
                adapter = CountriesDetailAdapter(requireContext(), borderNames, borderCca2)
            }
        }
    }

    private fun setBackgroundColor() {
        binding.ivFlag.load("https://flagcdn.com/w320/${args.cca2.lowercase()}.png") {
            // Disable hardware bitmaps as Palette needs to read the image's pixels.
            allowHardware(false)
            listener(
                onSuccess = { _, result ->
                    // Create the palette on a background thread.
                    Palette.Builder(result.drawable.toBitmap()).generate { palette ->
                        // Consume the palette.
                        val drawable = GradientDrawable(
                            GradientDrawable.Orientation.TOP_BOTTOM,
                            palette?.dominantSwatch?.let {
                                intArrayOf(
                                    ContextCompat.getColor(requireContext(), R.color.primary),
                                    it.rgb,
                                    ContextCompat.getColor(requireContext(), R.color.accent),
                                )
                            }
                        )
                        binding.clFragmentCountries.background = drawable
                    }
                }
            )
        }
    }

}
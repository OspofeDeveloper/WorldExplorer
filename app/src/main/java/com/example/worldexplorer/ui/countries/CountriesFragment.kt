package com.example.worldexplorer.ui.countries

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.core.view.doOnPreDraw
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.worldexplorer.R
import com.example.worldexplorer.databinding.FragmentCountriesBinding
import com.example.worldexplorer.domain.models.countries.CountriesModel
import com.example.worldexplorer.ui.countries.adapter.CountriesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.Normalizer

@AndroidEntryPoint
class CountriesFragment : Fragment() {

    private val countriesViewModel: CountriesViewModel by viewModels()
    private lateinit var countriesAdapter: CountriesAdapter
    private var _binding: FragmentCountriesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCountriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    override fun onResume() {
        super.onResume()
        initDropdownMenu()
    }

    override fun onPause() {
        super.onPause()
    }

    private fun initUI() {
        initDropdownMenu()
        initListeners()
        initUIState()
        setReturnAnimation()
    }

    private fun initListeners() {
        binding.autoCompleteText.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                countriesViewModel.getAllCountriesOrdered(position)
            }
    }

    private fun initDropdownMenu() {
        val orderByItems = resources.getStringArray(R.array.order_by_dropdown_options)

        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_menu_item, orderByItems)
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
                    when (it) {
                        CountriesState.Loading -> loadingState()
                        is CountriesState.Error -> errorState()
                        is CountriesState.Success -> successState(it)
                    }
                }
            }
        }
    }

    /** Retrasamos la transicion con postponeEnterTransition() hasta que el recyclerview
     * esté listo paras dibujars y por lo tanto esté medido. Cuando esté lista se reanuda
     * la transición. Tenemos que poner esto para que al volver atrás se haga también la
     * transicion */
    private fun setReturnAnimation() {
        postponeEnterTransition()
        binding.rvListCountries.doOnPreDraw {
            startPostponedEnterTransition()
        }
    }

    private fun loadingState() {
        binding.pbCountries.isVisible = true
    }

    private fun errorState() {
        binding.pbCountries.isVisible = false
    }

    private fun successState(state: CountriesState.Success) {
        binding.pbCountries.isVisible = false
        initRecyclerView(state.countries)
    }

    private fun initRecyclerView(countryList: List<CountriesModel>) {
        countriesAdapter =
            CountriesAdapter(countryList, onItemSelected = { country, imageView, textView ->
                val extras = FragmentNavigatorExtras(
                        imageView to country.cca2,
                        textView to country.name
                )
                findNavController().navigate(
                    CountriesFragmentDirections.actionCountriesFragmentToCountriesDetailFragment(
                        country.name,
                        country.cca2
                    ),
                    extras
                )
            })

        binding.rvListCountries.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = countriesAdapter
        }
    }
}

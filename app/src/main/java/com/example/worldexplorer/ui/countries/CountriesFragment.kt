package com.example.worldexplorer.ui.countries

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.doOnPreDraw
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
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
import com.example.worldexplorer.domain.models.countries.CountryBasicModel
import com.example.worldexplorer.ui.countries.adapter.CountriesAdapter
import com.example.worldexplorer.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

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

    private fun initUI() {
        initKeyboard()
        initDropdownMenu()
        initUIState()
        setReturnAnimation()
    }

    /** Usamos setSoftInputMode para controlar cómo se ajusta la ventana cuando el teclado virtual
     *  (soft keyboard) se muestra. Cuando se establece en SOFT_INPUT_ADJUST_PAN, la ventana de la
     *  aplicación se ajusta de manera que el contenido de la ventana no se redimensiona
     *  automáticamente para acomodar el teclado virtual. En su lugar, el sistema de Android
     *  intenta hacer que el contenido visible se desplace para que el foco actual (generalmente un
     *  campo de entrada) sea visible*/
    private fun initKeyboard() {
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
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
                        is Resource.Loading -> loadingState()
                        is Resource.Error -> errorState(it.error)
                        is Resource.Success -> successState(it.data)
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

    private fun errorState(error: String) {
        binding.pbCountries.isVisible = false
        Toast.makeText(context, error, Toast.LENGTH_LONG).show()
    }

    private fun successState(countries: List<CountryBasicModel>) {
        binding.pbCountries.isVisible = false
        initListeners(countries)
        initRecyclerView(countries)
    }

    private fun initListeners(countries: List<CountryBasicModel>) {
        initSearchEditTextListener(countries)
        initDropdownMenuListener()
    }

    private fun initSearchEditTextListener(countries: List<CountryBasicModel>) {
        binding.etSearchCountries.addTextChangedListener { userFilter ->
            val countriesFiltered =
                countries.filter { country ->
                    country.name.lowercase().contains(userFilter.toString().lowercase())
                }
            countriesAdapter.updateCountries(countriesFiltered)
        }
    }

    private fun initDropdownMenuListener() {
        binding.autoCompleteText.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                countriesViewModel.getAllCountriesOrdered(position)
            }
    }

    private fun initRecyclerView(countries: List<CountryBasicModel>) {
        countriesAdapter =
            CountriesAdapter(
                context,
                countries,
                onItemSelected = { country, imageView, textView ->
                    val extras = FragmentNavigatorExtras(
                        imageView to country.cca2,
                        textView to country.name
                    )
                    findNavController().navigate(
                        CountriesFragmentDirections.actionCountriesFragmentToCountriesDetailFragment(
                            country.name,
                            country.cca2,
                        ),
                        extras
                    )
                }
            )
        countriesAdapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY

        binding.rvListCountries.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = countriesAdapter
        }
    }
}

package com.example.worldexplorer.ui.travel

import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.view.doOnPreDraw
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.worldexplorer.R
import com.example.worldexplorer.databinding.FragmentTravelBinding
import com.example.worldexplorer.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class TravelFragment : Fragment() {

    private var _binding: FragmentTravelBinding? = null
    private val binding get() = _binding!!
    private val travelViewModel: TravelViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTravelBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    /** Cuando mostramos por pantalla el detalle del pais que ha salido entramos en el estado
     *  STOP, que es basicamente cuando el fragmento ya no es visible. En ese momento reiniciamos
     *  el state del viewmodel de tal forma que al volver a ver la pantalla se haga una nueva
     *  busqueda a otro país a mostrar diferente al que ya habiamos cargado*/
    override fun onStop() {
        super.onStop()
        travelViewModel.restartViewModel()
    }


    private fun initUI() {
        initUIState()
        initListeners()
        setReturnAnimation()
    }

    private fun initListeners() {
        binding.animationEarth.setOnClickListener {
            /**travelViewModel.getRandomCountry()*/
        }
    }

    private fun setReturnAnimation() {
        postponeEnterTransition()
        binding.ivSurpriseFlag.doOnPreDraw {
            startPostponedEnterTransition()
        }
    }

    /** La diferencia en usar lifecycleScope.launch o viewLifecycleOwner.lifecycleScope.launch
     *  es que con la segunda está suscrita al ciclo de vida de la UI del fragment, por lo tanto
     *  cuando el fragment pasa al estado STOPPED dejamos de ver la UI pero el fragment sigue ahi en
     *  segundo plano por lo tanto la corrutina se cancela y al
     *  volver al estado STARTED esta se vuelve a lanzar. Por eso usando esta solo hace 1 vez el
     *  bloque de repeatOnLifecycle cuando volvemos de STOPPED. En el otro caso parece que solo se
     *  cancela en el caso de destruir el fragment, cosa que no hacemos cuando nos envia a la pantalla
     *  de detalle, por lo tanto al volver a STARTED desde STOPPED se ejecuta tantas veces como veces
     *  ha pasado por el estado STARTED*/
    private fun initUIState() {
        viewLifecycleOwner.lifecycleScope.launch {
            /** repeatOnLifecycle lanza el bloque en una nueva corrutina cada vez que
             *   el ciclo de vida está en el estado STARTED (o superior) y la cancela cuando está en STOPPED.*/
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                /** Inicia el flujo y comienza a escuchar los valores.
                 **  Esto sucede cuando el ciclo de vida está en STARTED y deja de
                 ** recolectar cuando el ciclo de vida está en STOPPED. */
                travelViewModel.state.collect {
                    when (it) {
                        is Resource.Loading -> loadingState()
                        is Resource.Error -> errorState(it.error)
                        is Resource.Success -> successState(it.data)
                    }
                }
            }
        }
    }

    private fun successState(travelInfo: Pair<String, String>) {
        binding.ivSurpriseFlag.load("https://flagcdn.com/w320/${travelInfo.second.lowercase()}.png")
        rotateEarth(travelInfo)
    }

    private fun loadingState() {}

    private fun errorState(error: String) {
        Toast.makeText(context, error, Toast.LENGTH_LONG).show()
    }

    private fun rotateEarth(travelInfo: Pair<String, String>) {
        val timer = object : CountDownTimer(2000, 50) {
            val animation = binding.animationEarth

            override fun onTick(millisUntilFinished: Long) {
                animation.speed -= 0.5f
            }

            override fun onFinish() {
                animation.speed = 0f
                growFlag(travelInfo)
            }
        }

        binding.animationEarth.apply {
            speed = 20f
            playAnimation()
        }

        timer.start()
    }

    private fun growFlag(travelInfo: Pair<String, String>) {
        val growAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.grow)

        growAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
                binding.ivSurpriseFlag.isVisible = true
            }

            override fun onAnimationEnd(animation: Animation?) {
                Handler(Looper.getMainLooper()).postDelayed({
                    showCountryView(travelInfo)
                }, 500)
            }

            override fun onAnimationRepeat(animation: Animation?) {}
        })

        binding.ivSurpriseFlag.startAnimation(growAnimation)
    }

    private fun showCountryView(travelInfo: Pair<String, String>) {
        binding.ivSurpriseFlag.transitionName = travelInfo.second
        val extras = FragmentNavigatorExtras(
            binding.ivSurpriseFlag to travelInfo.second,
        )
        findNavController().navigate(
            TravelFragmentDirections.actionExchangeFragmentToCountriesDetailFragment(
                name = travelInfo.first,
                cca2 = travelInfo.second
            ),
            extras
        )
    }
}
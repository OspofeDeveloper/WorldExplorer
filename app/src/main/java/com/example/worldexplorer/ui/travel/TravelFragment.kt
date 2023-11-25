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
import com.example.worldexplorer.domain.models.travel.TravelModel
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
            travelViewModel.getRandomCountry()
        }
    }

    private fun setReturnAnimation() {
        postponeEnterTransition()
        binding.ivSurpriseFlag.doOnPreDraw {
            startPostponedEnterTransition()
        }
    }

    private fun initUIState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
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

    private fun successState(cca2Travel: TravelModel) {
        binding.ivSurpriseFlag.load(cca2Travel.imageUrl)
        rotateEarth(cca2Travel)
    }

    private fun loadingState() {}

    private fun errorState(error: String) {
        Toast.makeText(context, error, Toast.LENGTH_LONG).show()
    }

    private fun rotateEarth(cca2Travel: TravelModel) {
        val timer = object : CountDownTimer(2000, 50) {
            val animation = binding.animationEarth

            override fun onTick(millisUntilFinished: Long) {
                animation.speed -= 0.5f
            }

            override fun onFinish() {
                animation.speed = 0f
                growFlag(cca2Travel)
            }
        }

        binding.animationEarth.apply {
            speed = 20f
            playAnimation()
        }

        timer.start()
    }

    private fun growFlag(cca2Travel: TravelModel) {
        val growAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.grow)

        growAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
                binding.ivSurpriseFlag.isVisible = true
            }

            override fun onAnimationEnd(animation: Animation?) {
                Handler(Looper.getMainLooper()).postDelayed({
                    showCountryView(cca2Travel)
                }, 500)
            }

            override fun onAnimationRepeat(animation: Animation?) {}
        })

        binding.ivSurpriseFlag.startAnimation(growAnimation)
    }

    private fun showCountryView(cca2Travel: TravelModel) {
        binding.ivSurpriseFlag.transitionName = cca2Travel.cca2
        val extras = FragmentNavigatorExtras(
            binding.ivSurpriseFlag to cca2Travel.cca2,
        )
        findNavController().navigate(
            TravelFragmentDirections.actionExchangeFragmentToCountriesDetailFragment(
                name = cca2Travel.name,
                cca2 = cca2Travel.cca2
            ),
            extras
        )
    }
}
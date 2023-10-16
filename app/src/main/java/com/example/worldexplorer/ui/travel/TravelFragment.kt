package com.example.worldexplorer.ui.travel

import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.view.doOnPreDraw
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.example.worldexplorer.R
import com.example.worldexplorer.databinding.FragmentTravelBinding
import com.example.worldexplorer.ui.countries.CountriesFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.internal.wait


@AndroidEntryPoint
class TravelFragment : Fragment() {

    private var _binding: FragmentTravelBinding? = null
    private val binding get() = _binding!!

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

    private fun initUI() {
        initListeners()
        setReturnAnimation()
    }

    private fun setReturnAnimation() {
        postponeEnterTransition()
        binding.ivSurpriseFlag.doOnPreDraw {
            startPostponedEnterTransition()
        }
    }

    private fun initListeners() {
        binding.animationEarth.setOnClickListener {
            rotateEarth()
        }
    }

    private fun rotateEarth() {
        val timer = object : CountDownTimer(2700, 50) {
            val animation = binding.animationEarth

            override fun onTick(millisUntilFinished: Long) {
                animation.speed -= 0.5f
            }

            override fun onFinish() {
                animation.speed = 0f
                growFlag()
            }
        }

        binding.animationEarth.apply {
            speed = 20f
            playAnimation()
        }

        timer.start()
    }

    private fun growFlag() {
        val growAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.grow)

        growAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
                binding.ivSurpriseFlag.isVisible = true
            }

            override fun onAnimationEnd(animation: Animation?) {
                Handler(Looper.getMainLooper()).postDelayed({
                    showCountryView()
                }, 500)
            }

            override fun onAnimationRepeat(animation: Animation?) {}
        })

        binding.ivSurpriseFlag.startAnimation(growAnimation)
    }

    private fun showCountryView() {
        binding.ivSurpriseFlag.transitionName = "ES"
        val extras = FragmentNavigatorExtras(
            binding.ivSurpriseFlag to "ES",
        )
        findNavController().navigate(
            TravelFragmentDirections.actionExchangeFragmentToCountriesDetailFragment(
                name = "Spain",
                cca2 = "ES"
            ),
            extras
        )
    }
}
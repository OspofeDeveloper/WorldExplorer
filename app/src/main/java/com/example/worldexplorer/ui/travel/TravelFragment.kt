package com.example.worldexplorer.ui.travel

import android.graphics.drawable.AnimationDrawable
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
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.airbnb.lottie.LottieAnimationView
import com.example.worldexplorer.R
import com.example.worldexplorer.databinding.FragmentTravelBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class TravelFragment : Fragment() {

    private var _binding: FragmentTravelBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        initListeners()
    }

    private fun initListeners() {
        binding.animationEarth.setOnClickListener {
            rotateEarth()
        }
    }

    private fun rotateEarth() {
        val timer = object : CountDownTimer(2555, 50) {
            val animation = binding.animationEarth

            override fun onTick(millisUntilFinished: Long) {
                animation.speed -= 0.5f
            }

            override fun onFinish() {
                animation.speed = 0f
                appearFlag()
            }
        }

        binding.animationEarth.apply {
            speed = 20f
            playAnimation()
        }

        timer.start()
    }

    private fun appearFlag() {
        val appearAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.appear)

        appearAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
                binding.ivSurpriseFlag.isVisible = true
            }

            override fun onAnimationEnd(animation: Animation?) {
                growFlag()
            }

            override fun onAnimationRepeat(animation: Animation?) {}
        })

        binding.ivSurpriseFlag.startAnimation(appearAnimation)
    }

    private fun growFlag() {
        val growAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.grow)

        growAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                binding.ivSurpriseFlag.isVisible = false
                showCountryView()
            }

            override fun onAnimationRepeat(animation: Animation?) {}
        })

        binding.ivSurpriseFlag.startAnimation(growAnimation)
    }

    private fun showCountryView() {
        val disappearAnimation = AlphaAnimation(1.0f, 0.0f)
        disappearAnimation.duration = 200

        val appearAnimation = AlphaAnimation(0.0f, 1.0f)
        appearAnimation.duration = 1000

        disappearAnimation.setAnimationListener(object : Animation.AnimationListener{
            override fun onAnimationStart(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                binding.firstView.isVisible = false
                binding.secondView.isVisible = true
            }

            override fun onAnimationRepeat(animation: Animation?) {
            }
        })

        binding.firstView.startAnimation(disappearAnimation)
        binding.secondView.startAnimation(appearAnimation)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTravelBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}
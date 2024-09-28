package com.knu.onboarding

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.knu.common.view.viewBinding
import com.knu.home.MainActivity
import com.knu.onboarding.adapter.IntroAdapter
import com.knu.retastylog.onboarding.R
import com.knu.retastylog.onboarding.databinding.IntroFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class IntroFragment : Fragment(R.layout.intro_fragment) {
    private val viewModel: IntroViewModel by viewModels()
    private val binding by viewBinding(IntroFragmentBinding::bind)

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        setupViewPager()
        setupButtonListeners()

        // ViewModel 상태 구독
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.introCompleted.collect { isCompleted ->
                    if (isCompleted) {
                        navigateToMainActivity()
                    }
                }
            }
        }
    }

    private fun setupViewPager() {
        binding.vpIntro.adapter = IntroAdapter()
        TabLayoutMediator(binding.tlIntroIndicator, binding.vpIntro) { _, _ -> }.attach()

        binding.vpIntro.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    updateButtonState(position)
                }
            },
        )
    }

    private fun updateButtonState(position: Int) {
        if (position == MAX_STEP - 1) {
            binding.btnIntroNext.text = getString(R.string.intro_get_started)
            binding.btnIntroSkip.visibility = View.GONE
            binding.viewSkipBetweenNext.visibility = View.GONE

            val layoutParams = binding.btnIntroNext.layoutParams as LinearLayout.LayoutParams
            layoutParams.weight = 1f
            layoutParams.gravity = Gravity.CENTER
            binding.btnIntroNext.layoutParams = layoutParams
        } else {
            binding.btnIntroNext.text = getString(R.string.intro_next)
            binding.btnIntroSkip.visibility = View.VISIBLE
            binding.viewSkipBetweenNext.visibility = View.VISIBLE

            val layoutParams = binding.btnIntroNext.layoutParams as LinearLayout.LayoutParams
            layoutParams.weight = 0f
            layoutParams.gravity = Gravity.NO_GRAVITY
            binding.btnIntroNext.layoutParams = layoutParams
        }
    }

    private fun setupButtonListeners() {
        binding.btnIntroSkip.setOnClickListener {
            viewModel.completeIntro()
        }

        binding.btnIntroNext.setOnClickListener {
            if (binding.btnIntroNext.text == getString(R.string.intro_get_started)) {
                viewModel.completeIntro()
            } else {
                binding.vpIntro.currentItem++
            }
        }
    }

    private fun navigateToMainActivity() {
        val intent = Intent(requireContext(), MainActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    companion object {
        const val MAX_STEP = 3
    }
}

package com.knu.onboarding

import androidx.lifecycle.ViewModel
import com.knu.onboarding.repository.IntroRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class IntroViewModel
    @Inject
    constructor(
        private val introRepository: IntroRepository,
    ) : ViewModel() {
        private val _introCompleted = MutableStateFlow(false)
        val introCompleted: StateFlow<Boolean> get() = _introCompleted

        init {
            _introCompleted.value = introRepository.hasCompletedIntro()
        }

        fun completeIntro() {
            introRepository.setIntroCompleted(true)
            _introCompleted.value = true
        }
    }

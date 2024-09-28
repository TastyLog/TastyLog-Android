package com.knu.onboarding.repository

interface IntroRepository {
    fun hasCompletedIntro(): Boolean

    fun setIntroCompleted(completed: Boolean)
}

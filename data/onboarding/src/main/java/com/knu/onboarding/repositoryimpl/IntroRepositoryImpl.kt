package com.knu.onboarding.repositoryimpl

import com.knu.datastore.preferences.SharedPreferencesManager
import com.knu.onboarding.repository.IntroRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IntroRepositoryImpl
    @Inject
    constructor(
        private val preferencesManager: SharedPreferencesManager,
    ) : IntroRepository {
        override fun hasCompletedIntro(): Boolean {
            return preferencesManager.hasCompletedIntro()
        }

        override fun setIntroCompleted(completed: Boolean) {
            preferencesManager.setIntroCompleted(completed)
        }
    }

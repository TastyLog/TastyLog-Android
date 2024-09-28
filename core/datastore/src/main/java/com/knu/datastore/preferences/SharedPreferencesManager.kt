package com.knu.datastore.preferences

import android.content.SharedPreferences
import com.knu.datastore.preferences.SharedPreferencesKeys.KEY_HAS_COMPLETED_INTRO
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPreferencesManager
    @Inject
    constructor(
        private val sharedPreferences: SharedPreferences,
    ) {
        fun setIntroCompleted(completed: Boolean) {
            sharedPreferences.edit().putBoolean(KEY_HAS_COMPLETED_INTRO, completed).apply()
        }

        fun hasCompletedIntro(): Boolean {
            return sharedPreferences.getBoolean(KEY_HAS_COMPLETED_INTRO, false)
        }
    }

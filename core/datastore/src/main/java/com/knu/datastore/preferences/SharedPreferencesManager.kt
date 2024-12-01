package com.knu.datastore.preferences

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPreferencesManager
@Inject
constructor(
    private val sharedPreferences: SharedPreferences,
) {
    fun setIntroCompleted(completed: Boolean) {
        sharedPreferences.edit().putBoolean(SharedPreferencesKeys.KEY_HAS_COMPLETED_INTRO, completed).apply()
    }

    fun hasCompletedIntro(): Boolean {
        return sharedPreferences.getBoolean(SharedPreferencesKeys.KEY_HAS_COMPLETED_INTRO, false)
    }

    fun putStringList(key: String, list: List<String>) {
        sharedPreferences.edit().putStringSet(key, list.toSet()).apply()
    }


    fun getStringList(key: String): List<String> {
        val stringSet = sharedPreferences.getStringSet(key, emptySet()) ?: emptySet()
        return stringSet.toList()
    }
}

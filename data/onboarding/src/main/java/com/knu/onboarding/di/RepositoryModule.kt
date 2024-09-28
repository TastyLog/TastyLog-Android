package com.knu.onboarding.di

import com.knu.datastore.preferences.SharedPreferencesManager
import com.knu.onboarding.repository.IntroRepository
import com.knu.onboarding.repositoryimpl.IntroRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideIntroRepository(
        sharedPreferencesManager: SharedPreferencesManager,
    ): IntroRepository {
        return IntroRepositoryImpl(sharedPreferencesManager)
    }
}

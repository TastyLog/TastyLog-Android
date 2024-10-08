package com.knu.home.di

import com.knu.home.service.YoutuberService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object YoutuberServiceModule {
    @Provides
    @Singleton
    fun provideYoutuberService(retrofit: Retrofit): YoutuberService {
        return retrofit.create(YoutuberService::class.java)
    }
}

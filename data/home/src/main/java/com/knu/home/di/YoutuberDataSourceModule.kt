package com.knu.home.di

import com.knu.home.datasource.YoutuberDataSource
import com.knu.home.datasourceimpl.YoutuberDataSourceImpl
import com.knu.home.service.YoutuberService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object YoutuberDataSourceModule {

    @Provides
    @Singleton
    fun provideRestaurantDataSource(
        youtuberService: YoutuberService,
    ): YoutuberDataSource {
        return YoutuberDataSourceImpl(youtuberService)
    }
}

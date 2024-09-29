package com.knu.home.di

import com.knu.home.service.RestaurantService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RestaurantServiceModule {
    @Provides
    @Singleton
    fun provideRestaurantService(retrofit: Retrofit): RestaurantService {
        return retrofit.create(RestaurantService::class.java)
    }
}

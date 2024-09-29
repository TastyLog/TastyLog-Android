package com.knu.home.di

import com.knu.home.datasource.RestaurantDataSource
import com.knu.home.datasourceimpl.RestaurantDataSourceImpl
import com.knu.home.service.RestaurantService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    @Singleton
    fun provideRestaurantDataSource(
        restaurantService: RestaurantService
    ): RestaurantDataSource {
        return RestaurantDataSourceImpl(restaurantService)
    }
}

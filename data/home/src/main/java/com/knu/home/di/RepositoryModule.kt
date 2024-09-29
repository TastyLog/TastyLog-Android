package com.knu.home.di

import com.knu.home.datasource.RestaurantDataSource
import com.knu.home.mapper.RestaurantMapper
import com.knu.home.repository.RestaurantRepository
import com.knu.home.repositoryimpl.RestaurantRepositoryImpl
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
    fun provideRestaurantRepository(
        dataSource: RestaurantDataSource,
        mapper: RestaurantMapper
    ): RestaurantRepository {
        return RestaurantRepositoryImpl(dataSource, mapper)
    }
}

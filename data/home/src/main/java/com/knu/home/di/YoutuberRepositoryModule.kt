package com.knu.home.di

import com.knu.home.datasource.YoutuberDataSource
import com.knu.home.mapper.YoutuberMapper
import com.knu.home.repository.YoutuberRepository
import com.knu.home.repositoryimpl.YoutuberRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object YoutuberRepositoryModule {

    @Provides
    @Singleton
    fun provideRestaurantRepository(
        dataSource: YoutuberDataSource,
        mapper: YoutuberMapper,
    ): YoutuberRepository {
        return YoutuberRepositoryImpl(dataSource, mapper)
    }
}

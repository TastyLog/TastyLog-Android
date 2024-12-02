package com.knu.search.di

import com.knu.search.datasource.SearchRankDataSource
import com.knu.search.datasourceimpl.SearchRankDataSourceImpl
import com.knu.search.service.SearchRankService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SearchRankDataSourceModule {

    @Provides
    @Singleton
    fun provideSearchRankDataSource(
        searchRankService: SearchRankService,
    ): SearchRankDataSource {
        return SearchRankDataSourceImpl(searchRankService)
    }

}

package com.knu.search.di

import com.knu.datastore.preferences.SharedPreferencesManager
import com.knu.search.datasource.SearchRankDataSource
import com.knu.search.mapper.SearchRankMapper
import com.knu.search.repository.RecentKeyWordRepository
import com.knu.search.repository.SearchRankRepository
import com.knu.search.repositoryimpl.SearchRankRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import repositoryimpl.RecentKeyWordRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SearchRankRepositoryModule {

    @Provides
    @Singleton
    fun provideRecentKeyWordRepository(
        sharedPreferencesManager: SharedPreferencesManager,
    ): RecentKeyWordRepository {
        return RecentKeyWordRepositoryImpl(sharedPreferencesManager)
    }

    @Provides
    @Singleton
    fun provideSearchRankRepository(
        datasource: SearchRankDataSource,
        mapper: SearchRankMapper,
    ): SearchRankRepository {
        return SearchRankRepositoryImpl(datasource, mapper)
    }
}

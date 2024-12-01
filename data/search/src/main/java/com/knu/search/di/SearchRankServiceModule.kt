package com.knu.search.di

import com.knu.search.service.SearchRankService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SearchRankServiceModule {

    @Provides
    @Singleton
    fun provideSearchRankService(retrofit: Retrofit): SearchRankService {
        return retrofit.create(SearchRankService::class.java)
    }
}

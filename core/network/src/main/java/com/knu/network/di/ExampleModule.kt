package com.knu.network.di

import com.knu.network.api.ExampleApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ExampleModule {
    @Provides
    @Singleton
    fun provideExampleApi(retrofit: Retrofit): ExampleApi =
        retrofit.create(
            ExampleApi::class.java,
        )
}

package com.knu.home.repositoryimpl

import com.knu.home.datasource.RestaurantDataSource
import com.knu.home.entity.RestaurantEntity
import com.knu.home.mapper.RestaurantMapper
import com.knu.home.repository.RestaurantRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RestaurantRepositoryImpl(
    private val dataSource: RestaurantDataSource,
    private val mapper: RestaurantMapper,
) : RestaurantRepository {

    companion object {
        const val SUCCESS_CODE = 1
    }

    override suspend fun getRestaurantList(
        latitude: Double,
        longitude: Double,
        page: Int,
        size: Int,
        searchWord: String?,
    ): Flow<List<RestaurantEntity>> = flow {
        val response = dataSource.fetchRestaurantList(latitude, longitude, page, size, searchWord)
        if (response.code == SUCCESS_CODE) {
            val restaurants = mapper.mapToDomainList(response.data ?: emptyList())
            emit(restaurants)
        } else {
            emit(emptyList())
        }
    }
}

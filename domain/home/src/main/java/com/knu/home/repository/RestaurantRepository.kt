package com.knu.home.repository

import com.knu.home.entity.RestaurantEntity
import kotlinx.coroutines.flow.Flow

interface RestaurantRepository {
    suspend fun getRestaurantList(
        latitude: Double,
        longitude: Double,
        page: Int = 0,   // 기본값 0
        size: Int = 1000,   // 기본값 1000
        searchWord: String? = null,
    ): Flow<List<RestaurantEntity>>
}

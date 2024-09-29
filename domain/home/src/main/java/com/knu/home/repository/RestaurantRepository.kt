package com.knu.home.repository

import com.knu.home.entity.RestaurantEntity
import kotlinx.coroutines.flow.Flow

interface RestaurantRepository {
    suspend fun getRestaurantList(latitude: Double, longitude: Double): Flow<List<RestaurantEntity>>
}

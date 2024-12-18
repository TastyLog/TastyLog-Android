package com.knu.home.usecase

import com.knu.home.entity.RestaurantEntity
import com.knu.home.repository.RestaurantRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRestaurantListUseCase @Inject constructor(
    private val restaurantRepository: RestaurantRepository
) {
    suspend operator fun invoke(
        latitude: Double,
        longitude: Double,
        page: Int = 0,   // 기본값 0
        size: Int = 1000   // 기본값 1000
    ): Flow<List<RestaurantEntity>> {
        return restaurantRepository.getRestaurantList(latitude, longitude, page, size)
    }
}

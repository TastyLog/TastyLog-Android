package com.knu.home.usecase

import com.knu.home.entity.RestaurantEntity
import com.knu.home.repository.RestaurantRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRestaurantListUseCase @Inject constructor(
    private val restaurantRepository: RestaurantRepository
) {
    suspend operator fun invoke(latitude: Double, longitude: Double): Flow<List<RestaurantEntity>> {
        return restaurantRepository.getRestaurantList(latitude, longitude)
    }
}

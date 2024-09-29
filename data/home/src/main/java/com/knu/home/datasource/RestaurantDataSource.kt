package com.knu.home.datasource

import com.knu.home.response.ResponseRestaurantDto
import com.knu.network.model.BaseResponse

interface RestaurantDataSource {
    suspend fun fetchRestaurantList(latitude: Double, longitude: Double): BaseResponse<List<ResponseRestaurantDto>>
}

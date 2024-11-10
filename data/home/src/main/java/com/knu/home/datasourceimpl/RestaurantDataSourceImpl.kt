package com.knu.home.datasourceimpl

import com.knu.home.datasource.RestaurantDataSource
import com.knu.home.response.ResponseRestaurantDto
import com.knu.home.service.RestaurantService
import com.knu.network.model.BaseResponse
import javax.inject.Inject

class RestaurantDataSourceImpl @Inject constructor(
    private val restaurantService: RestaurantService,
) : RestaurantDataSource {

    override suspend fun fetchRestaurantList(
        latitude: Double,
        longitude: Double,
        page: Int,
        size: Int,
    ): BaseResponse<List<ResponseRestaurantDto>> {
        val response = restaurantService.getRestaurantList(latitude, longitude, page, size)
        return BaseResponse(
            code = response.code,
            message = response.message,
            data = response.data?.content ?: emptyList(),
        )
    }
}


package com.knu.home.service

import com.knu.home.response.ResponseRestaurantListDto
import com.knu.network.api.ApiRoutes
import com.knu.network.model.BaseResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RestaurantService {
    @GET(ApiRoutes.GET_RESTAURANT_LIST)
    suspend fun getRestaurantList(
        @Path("latitude") latitude: Double,
        @Path("longitude") longitude: Double,
        @Query("pages") page: Int = 0,
        @Query("size") size: Int = 1000,
    ): BaseResponse<ResponseRestaurantListDto>
}

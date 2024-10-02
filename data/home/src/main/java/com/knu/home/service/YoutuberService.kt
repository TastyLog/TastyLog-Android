package com.knu.home.service

import com.knu.home.response.ResponseYoutuberListDto
import com.knu.network.api.ApiRoutes
import com.knu.network.model.BaseResponse
import retrofit2.http.GET

interface YoutuberService {
    @GET(ApiRoutes.GET_YOUTUBER_LIST)
    suspend fun fetchYoutuberList(): BaseResponse<List<ResponseYoutuberListDto>>
}

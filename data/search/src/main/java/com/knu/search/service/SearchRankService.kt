package com.knu.search.service

import com.knu.network.model.BaseResponse
import com.knu.search.response.ResponseSearchRankDto
import retrofit2.http.GET

interface SearchRankService {
    @GET("api/v1/food/search/rank")
    suspend fun fetchSearchRank(): BaseResponse<List<ResponseSearchRankDto>>
}

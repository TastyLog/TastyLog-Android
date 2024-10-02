package com.knu.home.datasource

import com.knu.home.response.ResponseYoutuberListDto
import com.knu.network.model.BaseResponse

interface YoutuberDataSource {
    suspend fun fetchYoutuberList(): BaseResponse<List<ResponseYoutuberListDto>>
}

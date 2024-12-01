package com.knu.search.datasource

import com.knu.network.model.BaseResponse
import com.knu.search.response.ResponseSearchRankDto

interface SearchRankDataSource {
    suspend fun fetchSearchRank(): BaseResponse<List<ResponseSearchRankDto>>
}

package com.knu.search.datasourceimpl

import com.knu.network.model.BaseResponse
import com.knu.search.datasource.SearchRankDataSource
import com.knu.search.response.ResponseSearchRankDto
import com.knu.search.service.SearchRankService
import javax.inject.Inject

class SearchRankDataSourceImpl @Inject constructor(
    private val searchRankService: SearchRankService,
) : SearchRankDataSource {
    override suspend fun fetchSearchRank(): BaseResponse<List<ResponseSearchRankDto>> {
        val response = searchRankService.fetchSearchRank()
        return BaseResponse(
            code = response.code,
            message = response.message,
            data = response.data ?: emptyList(),
        )
    }
}

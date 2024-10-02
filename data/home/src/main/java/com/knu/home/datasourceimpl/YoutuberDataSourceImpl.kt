package com.knu.home.datasourceimpl

import com.knu.home.datasource.YoutuberDataSource
import com.knu.home.response.ResponseYoutuberListDto
import com.knu.home.service.YoutuberService
import com.knu.network.model.BaseResponse
import javax.inject.Inject

class YoutuberDataSourceImpl @Inject constructor(
    private val youtuberService: YoutuberService,
) : YoutuberDataSource {
    override suspend fun fetchYoutuberList(): BaseResponse<List<ResponseYoutuberListDto>> {
        return youtuberService.fetchYoutuberList()
    }
}

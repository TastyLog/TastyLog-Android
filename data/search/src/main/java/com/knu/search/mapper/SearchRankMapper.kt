package com.knu.search.mapper

import com.knu.search.entity.SearchRankEntity
import com.knu.search.response.ResponseSearchRankDto
import javax.inject.Inject

class SearchRankMapper @Inject constructor() {
    fun mapToEntity(dto: ResponseSearchRankDto): SearchRankEntity {
        return SearchRankEntity(
            keyword = dto.keyword,
            score = dto.score,
            rank = dto.rank,
            state = dto.state,
        )
    }

    fun mapToEntityList(dtoList: List<ResponseSearchRankDto>): List<SearchRankEntity> {
        return dtoList.map { mapToEntity(it) }
    }
}

package com.knu.search.usecase

import com.knu.search.entity.SearchRankEntity
import com.knu.search.repository.SearchRankRepository
import javax.inject.Inject

class SearchRankUseCase @Inject constructor(
    private val repository: SearchRankRepository,
) {
    suspend operator fun invoke(): List<SearchRankEntity> {
        return repository.fetchSearchRank()
    }

}

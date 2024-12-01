package com.knu.search.repository

import com.knu.search.entity.SearchRankEntity

interface SearchRankRepository {
    suspend fun fetchSearchRank(): List<SearchRankEntity>
}

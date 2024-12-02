package com.knu.search.repositoryimpl

import android.util.Log
import com.knu.search.datasource.SearchRankDataSource
import com.knu.search.entity.SearchRankEntity
import com.knu.search.mapper.SearchRankMapper
import com.knu.search.repository.SearchRankRepository
import javax.inject.Inject

class SearchRankRepositoryImpl @Inject constructor(
    private val remoteDataSource: SearchRankDataSource,
    private val mapper: SearchRankMapper,
) : SearchRankRepository {

    override suspend fun fetchSearchRank(): List<SearchRankEntity> {
        return try {
            val remoteResponse = remoteDataSource.fetchSearchRank()
            if (remoteResponse.code != 1) {
                throw Exception("API Error: ${remoteResponse.message}")
            }

            mapper.mapToEntityList(remoteResponse.data ?: emptyList())
        } catch (e: Exception) {
            Log.e("SearchRankRepositoryImpl", "Error fetching search rank", e)
            emptyList()
        }
    }
}

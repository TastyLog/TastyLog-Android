package com.knu.home.repositoryimpl

import com.knu.home.datasource.YoutuberDataSource
import com.knu.home.entity.YoutuberEntity
import com.knu.home.mapper.YoutuberMapper
import com.knu.home.repository.YoutuberRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class YoutuberRepositoryImpl @Inject constructor(
    private val youtuberDataSource: YoutuberDataSource,
    private val youtuberMapper: YoutuberMapper,
) : YoutuberRepository {
    override suspend fun fetchYoutuberList(): Flow<List<YoutuberEntity>> = flow {
        val response = youtuberDataSource.fetchYoutuberList()
        val youtubers = youtuberMapper.mapToDomainList(response.data ?: emptyList())
        emit(youtubers)
    }
}

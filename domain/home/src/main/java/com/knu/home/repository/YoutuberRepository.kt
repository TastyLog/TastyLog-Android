package com.knu.home.repository

import com.knu.home.entity.YoutuberEntity
import kotlinx.coroutines.flow.Flow

interface YoutuberRepository {
    suspend fun fetchYoutuberList(): Flow<List<YoutuberEntity>>
}

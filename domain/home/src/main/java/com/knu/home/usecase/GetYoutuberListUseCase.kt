package com.knu.home.usecase

import com.knu.home.entity.YoutuberEntity
import com.knu.home.repository.YoutuberRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetYoutuberListUseCase @Inject constructor(
    private val youtuberRepository: YoutuberRepository,
) {
    suspend operator fun invoke(): Flow<List<YoutuberEntity>> {
        return youtuberRepository.fetchYoutuberList()
    }
}

package com.knu.home.mapper

import com.knu.home.entity.YoutuberEntity
import com.knu.home.response.ResponseYoutuberListDto
import com.knu.retastylog.common.BuildConfig
import javax.inject.Inject

class YoutuberMapper @Inject constructor(){
    private val s3BaseUrl = BuildConfig.S3_BASE_URL

    private fun mapToDomain(dto: ResponseYoutuberListDto): YoutuberEntity {
        return YoutuberEntity(
            youtuberName = dto.youtuberName,
            youtuberProfileUrl = s3BaseUrl + dto.youtuberProfile,
            youtuberId = dto.youtuberId,
            channelId = dto.youtuberChannelId
        )
    }

    fun mapToDomainList(dtoList: List<ResponseYoutuberListDto>): List<YoutuberEntity> {
        return dtoList.map { mapToDomain(it) }
    }
}


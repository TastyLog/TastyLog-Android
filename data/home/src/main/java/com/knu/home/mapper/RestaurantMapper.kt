package com.knu.home.mapper

import com.knu.home.entity.RestaurantEntity
import com.knu.home.response.ResponseRestaurantDto
import javax.inject.Inject

class RestaurantMapper @Inject constructor() {
    fun mapToDomain(dto: ResponseRestaurantDto): RestaurantEntity {
        return RestaurantEntity(
            uniqueKey = dto.uniqueKey,
            name = dto.restaurantName,
            category = dto.category,
            phoneNumber = dto.phoneNumber,
            address = dto.address,
            youtuberProfile = dto.youtuberProfile,
            youtuberName = dto.youtuberName,
            youtuberLink = dto.youtuberLink,
            latitude = dto.latitude.toDouble(),
            longitude = dto.longitude.toDouble(),
            naverLink = dto.naverLink,
            totalReviews = dto.totalReviews,
            rating = dto.rating.toFloat(),
            representativeImage = dto.representativeImage,
            youtuberId = dto.youtuberId,
            channelId = dto.channelId,
            distance = dto.distance
        )
    }

    fun mapToDomainList(dtoList: List<ResponseRestaurantDto>): List<RestaurantEntity> {
        return dtoList.map { mapToDomain(it) }
    }
}

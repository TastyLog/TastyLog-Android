package com.knu.home.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseRestaurantDto(
    @SerialName("uniqueKey")
    val uniqueKey: String,
    @SerialName("restaurantName")
    val restaurantName: String,
    @SerialName("category")
    val category: String,
    @SerialName("phoneNumber")
    val phoneNumber: String?,
    @SerialName("address")
    val address: String,
    @SerialName("youtuberProfile")
    val youtuberProfile: String,
    @SerialName("youtuberName")
    val youtuberName: String,
    @SerialName("youtuberLink")
    val youtuberLink: String,
    @SerialName("latitude")
    val latitude: String,
    @SerialName("longitude")
    val longitude: String,
    @SerialName("naverLink")
    val naverLink: String,
    @SerialName("totalReviews")
    val totalReviews: Int,
    @SerialName("rating")
    val rating: String,
    @SerialName("representativeImage")
    val representativeImage: String,
    @SerialName("youtuberId")
    val youtuberId: Int,
    @SerialName("channelId")
    val channelId: String,
    @SerialName("distance")
    val distance: String
)

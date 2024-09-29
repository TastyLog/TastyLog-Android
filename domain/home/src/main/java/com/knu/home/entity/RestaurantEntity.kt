package com.knu.home.entity

data class RestaurantEntity(
    val uniqueKey: String,
    val name: String,
    val category: String,
    val phoneNumber: String?,
    val address: String,
    val youtuberProfile: String,
    val youtuberName: String,
    val youtuberLink: String,
    val latitude: Double,
    val longitude: Double,
    val naverLink: String,
    val totalReviews: Int,
    val rating: Float,
    val representativeImage: String,
    val youtuberId: Int,
    val channelId: String,
    val distance: String
)

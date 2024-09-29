package com.knu.home.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class ResponseRestaurantListDto(
    @SerialName("content")
    val content: List<ResponseRestaurantDto>
)

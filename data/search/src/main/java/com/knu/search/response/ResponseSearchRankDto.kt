package com.knu.search.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseSearchRankDto(
    @SerialName("keyword") val keyword: String,
    @SerialName("score") val score: Double,
    @SerialName("rank") val rank: Int,
    @SerialName("state") val state: String,
)

package com.knu.home.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseYoutuberListDto(
    @SerialName("youtuberName")
    val youtuberName: String,
    @SerialName("youtuberProfile")
    val youtuberProfile: String,
    @SerialName("youtuberId")
    val youtuberId: Int,
    @SerialName("youtuberChannelId")
    val youtuberChannelId: String
)

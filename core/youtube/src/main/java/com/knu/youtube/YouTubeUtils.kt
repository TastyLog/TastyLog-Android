package com.knu.youtube

object YouTubeUtils {
    fun extractYoutubeId(url: String): String {
        return url.substringAfter("v=").substringBefore("&")
    }
}

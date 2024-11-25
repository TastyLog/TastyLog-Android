package com.knu.youtube

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

class YouTubePlayerManager(
    private val playerView: YouTubePlayerView
) {
    private var youTubePlayer: YouTubePlayer? = null

    fun initialize(onReady: (YouTubePlayer) -> Unit) {
        playerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                this@YouTubePlayerManager.youTubePlayer = youTubePlayer
                onReady(youTubePlayer)
            }
        })
    }

    fun playVideo(videoId: String, startPosition: Float = 0f) {
        youTubePlayer?.loadVideo(videoId, startPosition)
    }

    fun cueVideo(videoId: String, startPosition: Float = 0f) {
        youTubePlayer?.cueVideo(videoId, startPosition)
    }

    fun pauseVideo() {
        youTubePlayer?.pause()
    }

    fun release() {
        youTubePlayer = null
    }
}

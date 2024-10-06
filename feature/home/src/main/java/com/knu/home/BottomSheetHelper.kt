package com.knu.home

import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.lifecycle.LifecycleOwner
import coil.load
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.knu.home.entity.RestaurantEntity
import com.knu.retastylog.home.R
import com.knu.retastylog.home.databinding.ItemBottomSheetBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback

class BottomSheetHelper private constructor(
    private val bottomSheetBehavior: BottomSheetBehavior<CoordinatorLayout>,
    private val bottomSheetBinding: ItemBottomSheetBinding,
    private val listButton: View,
    private val lifecycleOwner: LifecycleOwner,
) {
    private var youTubePlayer: YouTubePlayer? = null
    private var lastPlayedPosition: Float? = null
    private var currentVideoId: String? = null
    private var isBottomSheetCalledOnce = false

    // BottomSheet 초기 상태 설정
    fun initializeBottomSheet() {
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        bottomSheetBehavior.addBottomSheetCallback(createBottomSheetCallback())
        lifecycleOwner.lifecycle.addObserver(bottomSheetBinding.ypvRestaurant)
    }

    // BottomSheet 상태 변경 콜백 함수
    private fun createBottomSheetCallback() = object : BottomSheetBehavior.BottomSheetCallback() {
        override fun onStateChanged(bottomSheet: View, newState: Int) {
            when (newState) {
                BottomSheetBehavior.STATE_EXPANDED -> listButton.visibility = View.GONE
                BottomSheetBehavior.STATE_COLLAPSED, BottomSheetBehavior.STATE_HIDDEN -> {
                    listButton.visibility = View.VISIBLE
                    pauseYoutubeVideo()
                }
            }
        }

        override fun onSlide(bottomSheet: View, slideOffset: Float) {}
    }

    // 레스토랑 정보 & 바텀시트
    fun showBottomSheetWithRestaurantInfo(restaurant: RestaurantEntity) {
        val newVideoId = extractYoutubeId(restaurant.youtuberLink)
        if (currentVideoId != newVideoId) resetYoutubePlayerIfNeeded(newVideoId)
        displayRestaurantInfo(restaurant, newVideoId)
        setYoutubeIconClickListener(newVideoId)
        ensureBottomSheetVisible()
        openBottomSheet()
        setupButtons(restaurant)
    }

    private fun displayRestaurantInfo(restaurant: RestaurantEntity, newVideoId: String) {
        with(bottomSheetBinding) {
            tvBsRestaurantName.text = restaurant.name
            tvBsRestaurantCategory.text = restaurant.category
            rbBsRestaurantRating.setRating(restaurant.rating)
            tvBsRestaurantReviews.text = "${restaurant.totalReviews} 리뷰"
            tvBsRestaurantRate.text = "${restaurant.rating} / 5"
            tvBsRestaurantCall.text = restaurant.phoneNumber ?: "전화번호 없음"
            tvBsRestaurantAddress.text = restaurant.address
            tvBsRestaurantDistance.text = restaurant.distance
            ivBsRestaurantPicture.load(restaurant.representativeImage)
            ivBsYoutubeThumnail.load("https://img.youtube.com/vi/$newVideoId/0.jpg")
        }
    }

    private fun setYoutubeIconClickListener(newVideoId: String) {
        bottomSheetBinding.ivBsYoutubeIcon.setOnClickListener {
            playYoutubeVideo(newVideoId)
        }
    }

    private fun ensureBottomSheetVisible() {
        if (!isBottomSheetCalledOnce) {
            bottomSheetBinding.root.visibility = View.VISIBLE
            isBottomSheetCalledOnce = true
        }
    }


    // 유튜브 ID 추출
    private fun extractYoutubeId(youtuberLink: String): String = youtuberLink.split("v=")[1].split("&")[0]

    // YouTubePlayer 초기화 및 상태 리셋
    private fun resetYoutubePlayerIfNeeded(newVideoId: String) {
        currentVideoId = newVideoId
        lastPlayedPosition = null
        youTubePlayer = null
        initializeYoutubePlayer()
    }

    // 유튜브 플레이어 초기화
    private fun initializeYoutubePlayer() {
        with(bottomSheetBinding) {
            ivBsYoutubeThumnail.visibility = View.VISIBLE
            ivBsYoutubeIcon.visibility = View.VISIBLE
            ypvRestaurant.visibility = View.GONE

            ypvRestaurant.getYouTubePlayerWhenReady(
                object : YouTubePlayerCallback {
                    override fun onYouTubePlayer(youTubePlayer: YouTubePlayer) {
                        this@BottomSheetHelper.youTubePlayer = youTubePlayer
                        lastPlayedPosition?.let {
                            youTubePlayer.seekTo(it) // 마지막 재생 위치로 이동
                        }
                    }
                },
            )

        }
    }

    // 유튜브 동영상 재생
    private fun playYoutubeVideo(videoId: String) {
        with(bottomSheetBinding) {
            ivBsYoutubeThumnail.visibility = View.GONE
            ivBsYoutubeIcon.visibility = View.GONE
            ypvRestaurant.visibility = View.VISIBLE

            youTubePlayer?.let { player ->
                lastPlayedPosition?.let { player.seekTo(it) }
                player.loadVideo(videoId, lastPlayedPosition ?: 0f)
            }
        }
    }

    // 유튜브 동영상 일시정지
    private fun pauseYoutubeVideo() {
        youTubePlayer?.pause()
    }

    // BottomSheet 열기
    private fun openBottomSheet() {
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    // 버튼 생성
    private fun setupButtons(restaurant: RestaurantEntity) {
        val buttonsContainer = bottomSheetBinding.llButtonsContainer
        buttonsContainer.removeAllViews()
        val buttonFactory = ActionButtonFactory(bottomSheetBinding.root.context, buttonsContainer, restaurant)
        buttonFactory.addButtons()
    }


    companion object {
        fun from(rootView: View, listButton: View, lifecycleOwner: LifecycleOwner): BottomSheetHelper {
            val bottomSheetBinding = ItemBottomSheetBinding.bind(rootView.findViewById(R.id.bottomSheetLayout))
            val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetBinding.root)
            return BottomSheetHelper(bottomSheetBehavior, bottomSheetBinding, listButton, lifecycleOwner)
        }
    }
}

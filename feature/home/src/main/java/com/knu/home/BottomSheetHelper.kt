package com.knu.home

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.lifecycle.LifecycleOwner
import coil.load
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.kakao.sdk.share.ShareClient
import com.kakao.sdk.share.WebSharerClient
import com.kakao.sdk.template.model.Button
import com.kakao.sdk.template.model.Content
import com.kakao.sdk.template.model.FeedTemplate
import com.kakao.sdk.template.model.Link
import com.knu.designsystem.button.ActionButtonFactory
import com.knu.home.entity.RestaurantEntity
import com.knu.retastylog.home.R
import com.knu.retastylog.home.databinding.ItemBottomSheetBinding
import com.knu.youtube.YouTubePlayerManager
import com.knu.youtube.YouTubeUtils

class BottomSheetHelper private constructor(
    private val bottomSheetBehavior: BottomSheetBehavior<CoordinatorLayout>,
    private val bottomSheetBinding: ItemBottomSheetBinding,
    private val listButton: View,
    private val lifecycleOwner: LifecycleOwner,
) {
    private lateinit var youTubePlayerManager: YouTubePlayerManager
    private var currentVideoId: String? = null
    private var isBottomSheetCalledOnce = false

    // BottomSheet 초기 상태 설정
    fun initializeBottomSheet() {
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        bottomSheetBehavior.addBottomSheetCallback(createBottomSheetCallback())
        youTubePlayerManager = YouTubePlayerManager(bottomSheetBinding.ypvRestaurant)
        youTubePlayerManager.initialize { }
    }

    // BottomSheet 상태 변경 콜백 함수
    private fun createBottomSheetCallback() = object : BottomSheetBehavior.BottomSheetCallback() {
        override fun onStateChanged(bottomSheet: View, newState: Int) {
            when (newState) {
                BottomSheetBehavior.STATE_EXPANDED -> listButton.visibility = View.GONE
                BottomSheetBehavior.STATE_COLLAPSED, BottomSheetBehavior.STATE_HIDDEN -> {
                    listButton.visibility = View.VISIBLE
                    youTubePlayerManager.pauseVideo()
                }
            }
        }

        override fun onSlide(bottomSheet: View, slideOffset: Float) {}
    }

    // 레스토랑 정보 & 바텀시트
    fun showBottomSheetWithRestaurantInfo(restaurant: RestaurantEntity) {
        val newVideoId = YouTubeUtils.extractYoutubeId(restaurant.youtuberLink)
        if (currentVideoId != newVideoId) {
            currentVideoId = newVideoId
            youTubePlayerManager.cueVideo(newVideoId)
        }
        displayRestaurantInfo(restaurant, newVideoId)
        setupYoutubeIconClickListener(newVideoId)
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

    private fun setupYoutubeIconClickListener(newVideoId: String) {
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

    private fun playYoutubeVideo(videoId: String) {
        with(bottomSheetBinding) {
            ivBsYoutubeThumnail.visibility = View.GONE
            ivBsYoutubeIcon.visibility = View.GONE
            ypvRestaurant.visibility = View.VISIBLE
        }

        youTubePlayerManager.playVideo(videoId)
    }

    // BottomSheet 열기
    private fun openBottomSheet() {
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    // 버튼 생성
    private fun setupButtons(restaurant: RestaurantEntity) {
        val buttonsContainer = bottomSheetBinding.llButtonsContainer
        buttonsContainer.removeAllViews()

        val sendKakaoLinkAction = { sendKakaoLink(restaurant) }

        val buttonFactory = ActionButtonFactory(
            context = bottomSheetBinding.root.context,
            container = buttonsContainer,
            restaurant = restaurant,
            sendKakaoLinkAction = sendKakaoLinkAction, // 동작 주입
        )
        buttonFactory.addButtons()
    }

    // 카카오 링크 공유 동작
    private fun sendKakaoLink(restaurant: RestaurantEntity) {
        val defaultFeed = FeedTemplate(
            content = Content(
                title = restaurant.name,
                description = restaurant.address,
                imageUrl = restaurant.representativeImage,
                link = Link(mobileWebUrl = restaurant.naverLink, webUrl = restaurant.naverLink),
            ),
            buttons = listOf(Button("자세히 보기", Link(mobileWebUrl = restaurant.naverLink, webUrl = restaurant.naverLink))),
        )

        if (ShareClient.instance.isKakaoTalkSharingAvailable(bottomSheetBinding.root.context)) {
            ShareClient.instance.shareDefault(bottomSheetBinding.root.context, defaultFeed) { sharingResult, error ->
                if (error != null) {
                    Log.e("KakaoLink", "공유 실패", error)
                } else {
                    sharingResult?.intent?.let { bottomSheetBinding.root.context.startActivity(it) }
                }
            }
        } else {
            val sharerUrl = WebSharerClient.instance.makeDefaultUrl(defaultFeed)
            openWebPage(sharerUrl.toString())
        }
    }

    private fun openWebPage(url: String) {
        bottomSheetBinding.root.context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }


    companion object {
        fun from(rootView: View, listButton: View, lifecycleOwner: LifecycleOwner): BottomSheetHelper {
            val bottomSheetBinding = ItemBottomSheetBinding.bind(rootView.findViewById(R.id.bottomSheetLayout))
            val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetBinding.root)
            return BottomSheetHelper(bottomSheetBehavior, bottomSheetBinding, listButton, lifecycleOwner)
        }
    }
}

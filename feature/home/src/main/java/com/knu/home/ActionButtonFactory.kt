package com.knu.home

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Space
import androidx.core.content.ContextCompat
import com.google.android.material.card.MaterialCardView
import com.kakao.sdk.share.ShareClient
import com.kakao.sdk.share.WebSharerClient
import com.kakao.sdk.template.model.Button
import com.kakao.sdk.template.model.Content
import com.kakao.sdk.template.model.FeedTemplate
import com.kakao.sdk.template.model.Link
import com.knu.common.view.dpToPx
import com.knu.home.entity.RestaurantEntity
import com.knu.retastylog.home.R
import com.knu.retastylog.home.databinding.ButtonSelectorBinding

class ActionButtonFactory(
    private val context: Context,
    private val container: LinearLayout,
    private val restaurant: RestaurantEntity
) {

    fun addButtons() {
        createAndAddButton(
            iconRes = R.drawable.img_naver_logo,
            text = "네이버 지도로 보기",
            textColorRes = R.color.white,
            backgroundColorRes = com.knu.retastylog.designsystem.R.color.naver_color
        ) { openNaverMap(restaurant) }

        addSpaceBetweenButtons()

        createAndAddButton(
            iconRes = R.drawable.img_kakaomap_logo,
            text = "카카오맵으로 보기",
            textColorRes = R.color.black,
            backgroundColorRes = com.knu.retastylog.designsystem.R.color.kakao_color
        ) { openKakaoMap(restaurant) }

        addSpaceBetweenButtons()

        createAndAddButton(
            iconRes = R.drawable.img_kakao_logo,
            text = "카카오톡으로 공유하기",
            textColorRes = R.color.black,
            backgroundColorRes = com.knu.retastylog.designsystem.R.color.kakao_color
        ) { sendKakaoLink(restaurant) }
    }

    private fun createAndAddButton(
        iconRes: Int,
        text: String,
        textColorRes: Int,
        backgroundColorRes: Int,
        onClickAction: () -> Unit
    ) {
        val buttonBinding = ButtonSelectorBinding.inflate(LayoutInflater.from(context))

        buttonBinding.apply {
            ivBtn.setImageResource(iconRes)
            tvBtn.text = text
            tvBtn.setTextColor(ContextCompat.getColor(context, textColorRes))
            setupIconLayoutParams(ivBtn)

            cvIntent.setCardBackgroundColor(ContextCompat.getColor(context, backgroundColorRes))
            setCardHeight(cvIntent)

            // 버튼 클릭 리스너 추가
            cvIntent.setOnClickListener { onClickAction() }

            container.addView(root)
        }
    }

    private fun setupIconLayoutParams(iconView: ImageView) {
        val iconParams = iconView.layoutParams as LinearLayout.LayoutParams
        iconParams.width = 32.dpToPx()
        iconParams.height = 32.dpToPx()
        iconParams.marginEnd = 3.dpToPx()
        iconParams.marginStart = (container.measuredWidth / 3)
        iconView.layoutParams = iconParams
    }

    private fun setCardHeight(cardView: MaterialCardView) {
        val cardViewParams = cardView.layoutParams ?: ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        cardViewParams.height = 50.dpToPx()
        cardView.layoutParams = cardViewParams
    }

    private fun addSpaceBetweenButtons() {
        val space = Space(context).apply {
            layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 4.dpToPx())
        }
        container.addView(space)
    }

    // 네이버 지도 열기
    private fun openNaverMap(restaurant: RestaurantEntity) {
        restaurant.naverLink?.let { naverLink ->
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(naverLink))
            context.startActivity(intent)
        }
    }

    // 카카오맵 열기
    private fun openKakaoMap(restaurant: RestaurantEntity) {
        try {
            val intentUri = Uri.parse("kakaomap://look?p=${restaurant.latitude},${restaurant.longitude}")
            context.startActivity(Intent(Intent.ACTION_VIEW, intentUri))
        } catch (e: ActivityNotFoundException) {
            val kakaoMapUrl = "https://map.kakao.com/?urlX=${restaurant.longitude}&urlY=${restaurant.latitude}&name=${restaurant.name}"
            openWebPage(kakaoMapUrl)
        }
    }

    // 카카오톡 공유
    private fun sendKakaoLink(restaurant: RestaurantEntity) {
        val defaultFeed = FeedTemplate(
            content = Content(
                title = restaurant.name,
                description = restaurant.address,
                imageUrl = restaurant.representativeImage,
                link = Link(mobileWebUrl = restaurant.naverLink, webUrl = restaurant.naverLink)
            ),
            buttons = listOf(Button("자세히 보기", Link(mobileWebUrl = restaurant.naverLink, webUrl = restaurant.naverLink)))
        )

        if (ShareClient.instance.isKakaoTalkSharingAvailable(context)) {
            ShareClient.instance.shareDefault(context, defaultFeed) { sharingResult, error ->
                if (error != null) {
                    Log.e("KakaoLink", "공유 실패", error)
                } else {
                    sharingResult?.intent?.let { context.startActivity(it) }
                }
            }
        } else {
            val sharerUrl = WebSharerClient.instance.makeDefaultUrl(defaultFeed)
            openWebPage(sharerUrl.toString())
        }
    }

    private fun openWebPage(url: String) {
        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }
}

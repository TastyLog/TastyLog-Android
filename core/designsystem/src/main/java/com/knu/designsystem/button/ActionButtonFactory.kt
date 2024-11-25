package com.knu.designsystem.button

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Space
import androidx.core.content.ContextCompat
import com.google.android.material.card.MaterialCardView
import com.knu.common.view.dpToPx
import com.knu.home.entity.RestaurantEntity
import com.knu.retastylog.designsystem.R
import com.knu.retastylog.designsystem.databinding.ButtonSelectorBinding

class ActionButtonFactory(
    private val context: Context,
    private val container: LinearLayout,
    private val restaurant: RestaurantEntity,
    private val sendKakaoLinkAction: () -> Unit,
) {

    fun addButtons() {
        createAndAddButton(
            iconRes = com.knu.retastylog.designsystem.R.drawable.img_naver_logo,
            text = "네이버 지도로 보기",
            textColorRes = R.color.white,
            backgroundColorRes = com.knu.retastylog.designsystem.R.color.naver_color,
        ) { openNaverMap(restaurant) }

        addSpaceBetweenButtons()

        createAndAddButton(
            iconRes = com.knu.retastylog.designsystem.R.drawable.img_kakaomap_logo,
            text = "카카오맵으로 보기",
            textColorRes = R.color.black,
            backgroundColorRes = com.knu.retastylog.designsystem.R.color.kakao_color,
        ) { openKakaoMap(restaurant) }

        addSpaceBetweenButtons()

        createAndAddButton(
            iconRes = com.knu.retastylog.designsystem.R.drawable.img_kakao_logo,
            text = "카카오톡으로 공유하기",
            textColorRes = R.color.black,
            backgroundColorRes = com.knu.retastylog.designsystem.R.color.kakao_color,
        ) { sendKakaoLinkAction() }
    }

    private fun createAndAddButton(
        iconRes: Int,
        text: String,
        textColorRes: Int,
        backgroundColorRes: Int,
        onClickAction: () -> Unit,
    ) {
        val buttonBinding = ButtonSelectorBinding.inflate(LayoutInflater.from(context))

        buttonBinding.apply {
            ivBtn.setImageResource(iconRes)
            tvBtn.text = text
            tvBtn.setTextColor(ContextCompat.getColor(context, textColorRes))

            cvIntent.setCardBackgroundColor(ContextCompat.getColor(context, backgroundColorRes))
            setCardHeight(cvIntent)

            // 버튼 클릭 리스너 추가
            cvIntent.setOnClickListener { onClickAction() }

            container.addView(root)
        }
    }

    private fun setCardHeight(cardView: MaterialCardView) {
        val cardViewParams = cardView.layoutParams
            ?: ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
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
            val kakaoMapUrl =
                "https://map.kakao.com/?urlX=${restaurant.longitude}&urlY=${restaurant.latitude}&name=${restaurant.name}"
            openWebPage(kakaoMapUrl)
        }
    }

    private fun openWebPage(url: String) {
        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }
}

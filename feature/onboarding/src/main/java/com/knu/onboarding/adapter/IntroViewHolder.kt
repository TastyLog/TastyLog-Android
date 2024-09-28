package com.knu.onboarding.adapter

import android.animation.ObjectAnimator
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.knu.retastylog.onboarding.R
import com.knu.retastylog.onboarding.databinding.ItemIntroBinding

class IntroViewHolder(private val binding: ItemIntroBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(position: Int) {
        when (position) {
            0 -> bindPage(R.string.intro_description_1, R.drawable.intro1, 1300, 1300)
            1 -> bindPage(R.string.intro_description_2, R.drawable.intro2, 1000, 1000)
            2 -> bindPage(R.string.intro_description_3, R.drawable.intro3, 1200, 1200)
        }
    }

    private fun bindPage(
        descriptionRes: Int,
        imageRes: Int,
        width: Int,
        height: Int,
    ) {
        binding.tvIntroDescription.text = binding.root.context.getString(descriptionRes)
        binding.ivIntroPicture.setImageResource(imageRes)
        resizeImageView(binding.ivIntroPicture, width, height)
        animateImageView(binding.ivIntroPicture)
    }

    private fun resizeImageView(
        imageView: ImageView,
        width: Int,
        height: Int,
    ) {
        imageView.layoutParams =
            imageView.layoutParams.apply {
                this.width = width
                this.height = height
            }
    }

    private fun animateImageView(imageView: ImageView) {
        ObjectAnimator.ofFloat(imageView, View.ALPHA, 0f, 1f).apply {
            duration = 1000
            start()
        }
    }
}

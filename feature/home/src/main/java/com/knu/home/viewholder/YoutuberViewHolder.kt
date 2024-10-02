package com.knu.home.viewholder

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.knu.home.entity.YoutuberEntity
import com.knu.retastylog.home.databinding.ItemInfluencerChipBinding

class YoutuberViewHolder(private val binding: ItemInfluencerChipBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(youtuber: YoutuberEntity, isSelected: Boolean, onItemClick: (YoutuberEntity, Boolean) -> Unit) {
        binding.civItemInfluencerChip.load(youtuber.youtuberProfileUrl)
        binding.tvYoutuberName.text = youtuber.youtuberName
        setItemAlpha(isSelected) // 투명도 설정
        // 아이템 클릭 이벤트 처리
        binding.root.setOnClickListener {
            onItemClick(youtuber, !isSelected)
        }
    }
    private fun setItemAlpha(isSelected: Boolean) {
        val alphaValue = if (isSelected) SELECTED_ALPHA else UNSELECTED_ALPHA
        binding.civItemInfluencerChip.alpha = alphaValue
        binding.tvYoutuberName.alpha = alphaValue
    }
    companion object {
        private const val SELECTED_ALPHA = 0.6f
        private const val UNSELECTED_ALPHA = 1.0f
    }
}

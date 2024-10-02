package com.knu.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.knu.home.entity.YoutuberEntity
import com.knu.home.viewholder.YoutuberViewHolder
import com.knu.retastylog.home.databinding.ItemInfluencerChipBinding

class YoutuberAdapter(
    private val onItemClick: (YoutuberEntity, Boolean) -> Unit,
) : ListAdapter<YoutuberEntity, YoutuberViewHolder>(DIFF_CALLBACK) {

    private val selectedYoutubers = mutableSetOf<YoutuberEntity>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YoutuberViewHolder {
        val binding = ItemInfluencerChipBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return YoutuberViewHolder(binding)
    }

    override fun onBindViewHolder(holder: YoutuberViewHolder, position: Int) {
        val youtuber = getItem(position)
        val isSelected = isYoutuberSelected(youtuber)

        holder.bind(youtuber, isSelected) { clickedYoutuber, itemIsSelected ->
            val actualPosition = holder.bindingAdapterPosition
            if (actualPosition == NO_POSITION) return@bind

            updateSelectedYoutubers(clickedYoutuber, itemIsSelected)

            notifyItemChanged(actualPosition)
            onItemClick(clickedYoutuber, itemIsSelected)
        }
    }

    // 선택된 유튜버가 있는지 확인
    private fun isYoutuberSelected(youtuber: YoutuberEntity): Boolean {
        return selectedYoutubers.contains(youtuber)
    }

    // 유튜버 선택 상태 업데이트
    private fun updateSelectedYoutubers(youtuber: YoutuberEntity, isSelected: Boolean) {
        if (isSelected) {
            selectedYoutubers.add(youtuber)
        } else {
            selectedYoutubers.remove(youtuber)
        }
    }

    companion object {
        private const val NO_POSITION = RecyclerView.NO_POSITION

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<YoutuberEntity>() {
            override fun areItemsTheSame(oldItem: YoutuberEntity, newItem: YoutuberEntity): Boolean {
                return oldItem.youtuberId == newItem.youtuberId
            }

            override fun areContentsTheSame(oldItem: YoutuberEntity, newItem: YoutuberEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

}

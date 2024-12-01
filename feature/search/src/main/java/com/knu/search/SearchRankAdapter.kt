package com.knu.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.knu.retastylog.search.databinding.ItemRealTimeSearchTermBinding
import com.knu.search.entity.SearchRankEntity

class SearchRankAdapter(
    private val onKeywordClick: (String) -> Unit,
) : ListAdapter<SearchRankEntity, SearchRankAdapter.SearchRankViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchRankViewHolder {
        val binding = ItemRealTimeSearchTermBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchRankViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchRankViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class SearchRankViewHolder(private val binding: ItemRealTimeSearchTermBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(rank: SearchRankEntity) {
            binding.tvSearchRankKeyword.text = rank.keyword
            binding.tvSearchRankNumber.text = rank.rank.toString()

            // 상태에 따른 아이콘 설정
            val iconResId = when (rank.state) {
                "up" -> com.knu.retastylog.designsystem.R.drawable.icon_arrow_up
                "down" -> com.knu.retastylog.designsystem.R.drawable.icon_arrow_down
                "none" -> com.knu.retastylog.designsystem.R.drawable.icon_arrow_same
                "new" -> com.knu.retastylog.designsystem.R.drawable.icon_arrow_new
                else -> com.knu.retastylog.designsystem.R.drawable.icon_arrow_same
            }
            binding.ivSearchRankPicture.setImageResource(iconResId)

            binding.root.setOnClickListener {
                onKeywordClick(rank.keyword)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<SearchRankEntity>() {
            override fun areItemsTheSame(
                oldItem: SearchRankEntity,
                newItem: SearchRankEntity,
            ): Boolean {
                return oldItem.rank == newItem.rank
            }

            override fun areContentsTheSame(
                oldItem: SearchRankEntity,
                newItem: SearchRankEntity,
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}

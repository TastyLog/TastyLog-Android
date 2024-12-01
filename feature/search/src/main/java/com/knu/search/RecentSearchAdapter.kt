package com.knu.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.knu.retastylog.search.R

class RecentSearchAdapter(
    private val onKeywordClick: (String) -> Unit,
    private val onDeleteClick: (String) -> Unit
) : ListAdapter<String, RecentSearchAdapter.RecentSearchViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentSearchViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_search, parent, false)
        return RecentSearchViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecentSearchViewHolder, position: Int) {
        val keyword = getItem(position)
        holder.bind(keyword)
    }

    inner class RecentSearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val keywordTextView: TextView = itemView.findViewById(R.id.textview_search_keyword)
        private val deleteImageView: ImageView = itemView.findViewById(R.id.imageview_delete_search_keyword)

        fun bind(keyword: String) {
            keywordTextView.text = keyword
            keywordTextView.setOnClickListener {
                onKeywordClick(keyword)
            }
            deleteImageView.setOnClickListener {
                onDeleteClick(keyword)
            }
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }
}

package com.kms.list

import androidx.recyclerview.widget.DiffUtil
import com.knu.home.entity.RestaurantEntity

class RestaurantDiffCallback : DiffUtil.ItemCallback<RestaurantEntity>() {
    override fun areItemsTheSame(oldItem: RestaurantEntity, newItem: RestaurantEntity): Boolean {
        return oldItem.uniqueKey == newItem.uniqueKey
    }

    override fun areContentsTheSame(oldItem: RestaurantEntity, newItem: RestaurantEntity): Boolean {
        return oldItem == newItem
    }
}

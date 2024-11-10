package com.kms.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.knu.home.entity.RestaurantEntity
import com.knu.retastylog.list.databinding.ItemListRestaurantBinding

class RestaurantAdapter(
    private val onItemClicked: (RestaurantEntity) -> Unit,
) : ListAdapter<RestaurantEntity, RestaurantViewHolder>(RestaurantDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val binding = ItemListRestaurantBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RestaurantViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        val restaurant = getItem(position)
        holder.bind(restaurant, onItemClicked)
    }
}

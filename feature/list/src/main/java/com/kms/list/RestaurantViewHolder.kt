package com.kms.list

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.knu.home.entity.RestaurantEntity
import com.knu.retastylog.list.databinding.ItemListRestaurantBinding

class RestaurantViewHolder(private val binding: ItemListRestaurantBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(restaurant: RestaurantEntity, onItemClicked: (RestaurantEntity) -> Unit) {
        binding.apply {
            tvListRestaurantName.text = restaurant.name
            tvListRestaurantCategory.text = restaurant.category
            tvListRestaurantDistance.text = restaurant.distance
            tvListRestaurantReviewCount.text = "${restaurant.totalReviews} 리뷰"
            rbListRestaurantRating.setRating(restaurant.rating)
            tvListRestaurantRating.text = "${restaurant.rating} / 5"
            ivListRestaurantPicture.load(restaurant.representativeImage)
            civListRestaurantInfluencer.load(restaurant.youtuberProfile)

            root.setOnClickListener {
                onItemClicked(restaurant)
            }
        }
    }
}

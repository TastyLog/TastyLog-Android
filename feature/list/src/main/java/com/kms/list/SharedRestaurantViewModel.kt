package com.kms.list

import androidx.lifecycle.ViewModel
import com.knu.home.entity.RestaurantEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SharedRestaurantViewModel @Inject constructor() : ViewModel() {

    private val _selectedRestaurant = MutableStateFlow<RestaurantEntity?>(null)
    val selectedRestaurant: StateFlow<RestaurantEntity?> get() = _selectedRestaurant

    fun setSelectedRestaurant(restaurant: RestaurantEntity) {
        _selectedRestaurant.value = restaurant
    }
}

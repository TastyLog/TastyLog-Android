package com.knu.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.knu.home.entity.RestaurantEntity
import com.knu.home.usecase.GetRestaurantListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RestaurantViewModel @Inject constructor(
    private val getRestaurantListUseCase: GetRestaurantListUseCase,
) : ViewModel() {

    private val _restaurantList = MutableStateFlow<List<RestaurantEntity>>(emptyList())
    val restaurantList: StateFlow<List<RestaurantEntity>> get() = _restaurantList

    fun loadRestaurantList(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            getRestaurantListUseCase(latitude, longitude)
                .catch {
                    _restaurantList.value = emptyList()
                }
                .collect { restaurantList ->
                    _restaurantList.value = restaurantList
                }
        }
    }
}

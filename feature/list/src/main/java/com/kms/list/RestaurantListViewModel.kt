package com.kms.list

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
class RestaurantListViewModel @Inject constructor(
    private val getRestaurantListUseCase: GetRestaurantListUseCase,
) : ViewModel() {
    private var currentPage = 0
    private val pageSize = 40

    private val _isLastPage = MutableStateFlow(false)
    val isLastPage: StateFlow<Boolean> get() = _isLastPage

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _restaurantList = MutableStateFlow<List<RestaurantEntity>>(emptyList())
    val restaurantList: StateFlow<List<RestaurantEntity>> get() = _restaurantList

    fun loadRestaurantList(latitude: Double, longitude: Double) {
        if (_isLoading.value || _isLastPage.value) return
        _isLoading.value = true

        viewModelScope.launch {
            getRestaurantListUseCase(latitude, longitude, currentPage, pageSize)
                .catch {
                    _isLoading.value = false
                }
                .collect { restaurantList ->
                    val distinctRestaurantList = restaurantList.distinctBy { it.uniqueKey }
                    _restaurantList.value += distinctRestaurantList

                    currentPage++
                    _isLastPage.value = restaurantList.size < pageSize
                    _isLoading.value = false
                }
        }
    }
}

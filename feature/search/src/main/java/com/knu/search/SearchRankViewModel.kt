package com.knu.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.knu.search.entity.SearchRankEntity
import com.knu.search.usecase.SearchRankUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchRankViewModel @Inject constructor(
    private val searchRankUseCase: SearchRankUseCase,
) : ViewModel() {

    private val _searchRankState = MutableStateFlow<List<SearchRankEntity>>(emptyList())
    val searchRankState: StateFlow<List<SearchRankEntity>> get() = _searchRankState

    fun fetchSearchRank() {
        viewModelScope.launch {
            try {
                val searchRanks = searchRankUseCase()
                _searchRankState.value = searchRanks
                Log.d("SearchRankViewModel", "Fetched search ranks: $searchRanks")
            } catch (e: Exception) {
                Log.e("SearchRankViewModel", "Error fetching search rank", e)
            }
        }
    }
}

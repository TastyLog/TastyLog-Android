package com.knu.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.knu.home.entity.YoutuberEntity
import com.knu.home.usecase.GetYoutuberListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class YoutuberViewModel @Inject constructor(
    private val getYoutuberListUseCase: GetYoutuberListUseCase,
) : ViewModel() {
    private val _youtuberList = MutableStateFlow<List<YoutuberEntity>>(emptyList())
    val youtuberList: StateFlow<List<YoutuberEntity>> = _youtuberList

    init {
        fetchYoutuberList()
    }

    private fun fetchYoutuberList() {
        viewModelScope.launch {
            getYoutuberListUseCase().collect { youtubers ->
                _youtuberList.value = youtubers
            }
        }
    }
}

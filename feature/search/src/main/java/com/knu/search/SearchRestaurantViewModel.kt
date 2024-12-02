package com.knu.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.knu.home.usecase.GetRestaurantListUseCase
import com.knu.search.repository.RecentKeyWordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchRestaurantViewModel @Inject constructor(
    private val getRestaurantListUseCase: GetRestaurantListUseCase,
    private val recentKeyWordRepository: RecentKeyWordRepository,
) : ViewModel() {

    private val _recentKeywords = MutableStateFlow<List<String>>(emptyList())
    val recentKeywords: StateFlow<List<String>> get() = _recentKeywords

    init {
        loadRecentKeywords() // 초기화 시 로컬 저장소에서 검색어 불러오기
    }

    /**
     * 최근 검색어 추가
     */
    fun addRecentKeyword(keyword: String) {
        val updatedKeywords = (_recentKeywords.value + keyword).distinct().takeLast(10) // 최대 10개 유지
        _recentKeywords.value = updatedKeywords

        // 로컬 저장소에 업데이트
        saveRecentKeywords(updatedKeywords)
    }

    /**
     * 특정 검색어 삭제
     */
    fun deleteRecentKeyword(keyword: String) {
        val updatedKeywords = _recentKeywords.value.filter { it != keyword }
        _recentKeywords.value = updatedKeywords

        // 로컬 저장소에 업데이트
        saveRecentKeywords(updatedKeywords)
    }

    /**
     * 최근 검색어 전체 삭제
     */
    fun clearRecentKeywords() {
        _recentKeywords.value = emptyList()

        // 로컬 저장소에서 삭제
        saveRecentKeywords(emptyList())
    }

    /**
     * 로컬 저장소에서 검색어 로드
     */
    fun loadRecentKeywords() {
        viewModelScope.launch {
            val keywords = recentKeyWordRepository.getStringList(RECENT_KEYWORDS_KEY)
            _recentKeywords.value = keywords
        }
    }

    /**
     * 로컬 저장소에 검색어 저장
     */
    private fun saveRecentKeywords(keywords: List<String>) {
        viewModelScope.launch {
            recentKeyWordRepository.putStringList(RECENT_KEYWORDS_KEY, keywords)
        }
    }

    companion object {
        private const val RECENT_KEYWORDS_KEY = "recent_keywords"
    }
}


package com.knu.search

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.knu.common.view.viewBinding
import com.knu.retastylog.search.databinding.ActivitySearchRestaurantBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class SearchRestaurantActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivitySearchRestaurantBinding::inflate)
    private val viewModel: SearchRestaurantViewModel by viewModels()
    private val searchRankViewModel: SearchRankViewModel by viewModels()


    private val recentSearchAdapter by lazy {
        RecentSearchAdapter(
            onKeywordClick = ::onKeywordClick,
            onDeleteClick = viewModel::deleteRecentKeyword,
        )
    }

    private val searchRankAdapter by lazy {
        SearchRankAdapter(::onKeywordClick)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupUI()
        setupListeners()
        observeViewModels()
        fetchSearchRankData()
        updateTime()
        viewModel.loadRecentKeywords()
    }

    /**
     * UI 설정
     */
    private fun setupUI() {
        setupRecyclerView(binding.rvSearchRestaurantRecentSearch, recentSearchAdapter)
        setupRecyclerView(binding.rvSearchRestaurantMostSearch, searchRankAdapter)
    }

    private fun setupRecyclerView(recyclerView: RecyclerView, adapter: RecyclerView.Adapter<*>) {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    /**
     * ViewModel 관찰
     */
    private fun observeViewModels() {
        observeRecentKeywords()
        observeSearchRank()
    }

    private fun observeRecentKeywords() {
        lifecycleScope.launch {
            viewModel.recentKeywords.collect { keywords ->
                Log.d("SearchRestaurantActivity", "Recent keywords updated: $keywords")
                recentSearchAdapter.submitList(keywords)
            }
        }
    }

    private fun observeSearchRank() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                searchRankViewModel.searchRankState.collect { searchRanks ->
                    Log.d("SearchRestaurantActivity", "Search rank data updated: $searchRanks")
                    searchRankAdapter.submitList(searchRanks)
                }
            }
        }
    }

    private fun fetchSearchRankData() {
        Log.d("SearchRestaurantActivity", "Fetching search rank data.")
        searchRankViewModel.fetchSearchRank()
        updateTime() // 데이터 갱신 시 시간도 업데이트
    }

    private fun updateTime() {
        val currentTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
        binding.tvSearchRestaurantUpdateTime.text = "$currentTime 기준"
    }

    /**
     * 사용자 이벤트 처리
     */
    private fun setupListeners() {
        binding.edtSearchRestaurant.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val keyword = binding.edtSearchRestaurant.text.toString().trim()
                if (keyword.isNotEmpty()) {
                    viewModel.addRecentKeyword(keyword)
                    sendResultAndFinish(keyword)
                }
                true
            } else {
                false
            }
        }

        binding.tvSearchRestaurantCancel.setOnClickListener { finish() }
        binding.tvSearchRestaurantAllDelete.setOnClickListener { viewModel.clearRecentKeywords() }
    }

    private fun onKeywordClick(keyword: String) {
        binding.edtSearchRestaurant.setText(keyword)
        sendResultAndFinish(keyword)
    }

    private fun sendResultAndFinish(keyword: String) {
        val resultIntent = Intent().apply { putExtra("searchKeyword", keyword) }
        setResult(RESULT_OK, resultIntent)
        finish()
    }
}

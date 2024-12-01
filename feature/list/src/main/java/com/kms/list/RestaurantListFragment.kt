package com.kms.list

import android.content.Intent
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.knu.common.view.viewBinding
import com.knu.navigation.NavigationActions
import com.knu.navigation.NavigationHandler
import com.knu.retastylog.list.R
import com.knu.retastylog.list.databinding.ListRestaurantFragmentBinding
import com.knu.search.SearchRestaurantActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RestaurantListFragment : Fragment(R.layout.list_restaurant_fragment) {

    private val binding by viewBinding(ListRestaurantFragmentBinding::bind)
    private val restaurantListViewModel: RestaurantListViewModel by viewModels()
    private val sharedRestaurantViewModel: SharedRestaurantViewModel by activityViewModels()

    private lateinit var location: Location

    private val searchActivityLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == AppCompatActivity.RESULT_OK) {
                val keyword = result.data?.getStringExtra("searchKeyword")
                keyword?.let {
                    updateSearchField(it)
                    searchRestaurants(it)
                }
            }
        }

    private val restaurantAdapter by lazy {
        RestaurantAdapter { restaurantEntity ->
            sharedRestaurantViewModel.setSelectedRestaurant(restaurantEntity)
            val action = NavigationActions.ToDetail(restaurantEntity.uniqueKey)
            (activity as? NavigationHandler)?.navigate(action)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        location = getLocationFromArguments()
        setupRecyclerView()
        observeRestaurantList()

        if (!restaurantListViewModel.isSearchActive.value) {
            restaurantListViewModel.loadRestaurantList(location.latitude, location.longitude)
        }

        binding.edtListRestaurantSearch.setOnClickListener {
            val intent = Intent(requireContext(), SearchRestaurantActivity::class.java)
            searchActivityLauncher.launch(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        val isSearchActive = restaurantListViewModel.isSearchActive.value
        val lastSearchKeyword = restaurantListViewModel.lastSearchKeyword.value
        if (isSearchActive && !lastSearchKeyword.isNullOrEmpty()) {
            updateSearchField(lastSearchKeyword)
        }
    }

    private fun getLocationFromArguments(): Location {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(LOCATION_KEY, Location::class.java) ?: Location("").apply {
                latitude = DEFAULT_LATITUDE
                longitude = DEFAULT_LONGITUDE
            }
        } else {
            @Suppress("DEPRECATION")
            arguments?.getParcelable(LOCATION_KEY) ?: Location("").apply {
                latitude = DEFAULT_LATITUDE
                longitude = DEFAULT_LONGITUDE
            }
        }
    }

    private fun setupRecyclerView() {
        binding.rvListRestaurant.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = restaurantAdapter

            addOnScrollListener(
                object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)
                        val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                        val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                        val totalItemCount = layoutManager.itemCount

                        val isSearchActive = restaurantListViewModel.isSearchActive.value
                        if (lastVisibleItemPosition >= (totalItemCount * 0.8).toInt() &&
                            !restaurantListViewModel.isLoading.value &&
                            !restaurantListViewModel.isLastPage.value &&
                            !isSearchActive
                        ) {
                            restaurantListViewModel.loadRestaurantList(location.latitude, location.longitude)
                        }
                    }
                },
            )
        }
    }

    private fun observeRestaurantList() {
        viewLifecycleOwner.lifecycleScope.launch {
            restaurantListViewModel.restaurantList.collect { restaurants ->
                restaurantAdapter.submitList(restaurants)
            }
        }
    }

    private fun updateSearchField(keyword: String) {
        binding.edtListRestaurantSearch.setText(keyword)
    }

    private fun searchRestaurants(keyword: String) {
        if (restaurantListViewModel.isSearchActive.value) return // 이미 검색 중이면 API 호출 방지
        restaurantListViewModel.resetState() // 상태 초기화
        restaurantListViewModel.searchRestaurants(location.latitude, location.longitude, keyword)
    }

    companion object {
        private const val LOCATION_KEY = "location"
        private const val DEFAULT_LATITUDE = 37.566610
        private const val DEFAULT_LONGITUDE = 126.9783882

        fun newInstance(location: Location): RestaurantListFragment {
            return RestaurantListFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(LOCATION_KEY, location)
                }
            }
        }
    }
}

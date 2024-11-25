package com.knu.home

import android.location.Location
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.knu.common.view.SpaceItemDecoration
import com.knu.common.view.viewBinding
import com.knu.home.adapter.YoutuberAdapter
import com.knu.home.entity.RestaurantEntity
import com.knu.home.entity.YoutuberEntity
import com.knu.home.utils.createCustomMarker
import com.knu.navigation.NavigationActions
import com.knu.retastylog.home.R
import com.knu.retastylog.home.databinding.MapFragmentBinding
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.NaverMap
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MapFragment : Fragment(R.layout.map_fragment) {

    private val binding by viewBinding(MapFragmentBinding::bind)
    private lateinit var naverMap: NaverMap
    private lateinit var location: Location
    private lateinit var youtuberAdapter: YoutuberAdapter

    private val restaurantViewModel: RestaurantViewModel by viewModels()
    private val youtuberViewModel: YoutuberViewModel by viewModels()

    private lateinit var bottomSheetHelper: BottomSheetHelper

    private val selectedYoutubers = mutableSetOf<YoutuberEntity>()
    private val markers = mutableMapOf<String, Marker>() // 레스토랑 uniqueKey와 마커를 연결
    private var allRestaurantList: List<RestaurantEntity> = emptyList() // 전체 레스토랑 리스트

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mvHome.onCreate(savedInstanceState) // MapView 초기화
        initializeBottomSheetHelper() // BottomSheet 초기화
        setupMapView()  // 지도 설정
        setupLocation() // 위치 설정
        setupRecyclerView() // 유튜버 리스트 설정
        setupListRestaurantButtonClickListener() // 리스트 버튼 설정
        observeYoutuberList()
        observeRestaurantList()
    }

    // MapView 초기화 + 레스트랑 리스트 로드
    private fun setupMapView() {
        binding.mvHome.getMapAsync { map ->
            naverMap = map
            initializeMap() // 지도 초기화
            loadRestaurantList() // 레스토랑 리스트 로드
        }
    }

    private fun initializeBottomSheetHelper() {
        val listButton = binding.btnListRestaurantDialog
        bottomSheetHelper = BottomSheetHelper.from(binding.root, listButton, viewLifecycleOwner)
        bottomSheetHelper.initializeBottomSheet()
    }

    // 위치 정보 설정
    private fun setupLocation() {
        location = getLocationFromArguments()
    }

    // RecyclerView 설정, 유튜버 클릭 시 마커 필터링
    private fun setupRecyclerView() {
        youtuberAdapter = YoutuberAdapter { clickedYoutuber, isSelected ->
            if (isSelected) {
                selectedYoutubers.add(clickedYoutuber)
            } else {
                selectedYoutubers.remove(clickedYoutuber)
            }
            // 마커 업데이트
            updateMapMarkersBySelectedYoutubers()
        }

        binding.rvInfluencerChip.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = youtuberAdapter
            addItemDecoration(SpaceItemDecoration(16))
        }
    }

    private fun setupListRestaurantButtonClickListener() {
        binding.btnListRestaurantDialog.setOnClickListener {
            val action = NavigationActions.ToList(location)
            (activity as? MainActivity)?.navigate(action)
        }
    }

    // 지도 초기화, 현재 위치로 카메라 포지션 설정
    private fun initializeMap() {
        val cameraPosition = CameraPosition(LatLng(location.latitude, location.longitude), 15.0)
        naverMap.cameraPosition = cameraPosition
    }

    private fun observeRestaurantList() {
        viewLifecycleOwner.lifecycleScope.launch {
            restaurantViewModel.restaurantList.collect { restaurantList ->
                allRestaurantList = restaurantList
                updateMapMarkers(restaurantList) // 지도에 마커 업데이트
            }
        }
    }

    private fun observeYoutuberList() {
        viewLifecycleOwner.lifecycleScope.launch {
            youtuberViewModel.youtuberList.collect { youtuberList ->
                youtuberAdapter.submitList(youtuberList) // 유튜버 리스트를 어댑터로 전달
            }
        }
    }

    // 마커 필터링
    private fun updateMapMarkersBySelectedYoutubers() {
        val allRestaurants = restaurantViewModel.restaurantList.value

        val filteredRestaurants = if (selectedYoutubers.isEmpty()) {
            allRestaurants
        } else {
            allRestaurants.filter { restaurant ->
                selectedYoutubers.any { it.youtuberId == restaurant.youtuberId }
            }
        }

        updateMapMarkers(filteredRestaurants)
    }

    // 레스토랑 리스트 기반으로 마커를 지도에 업데이트
    private fun updateMapMarkers(restaurantList: List<RestaurantEntity>) {
        markers.values.forEach { it.map = null }
        markers.clear()

        restaurantList.forEach { restaurant ->
            lifecycleScope.launch {
                val markerBitmap = createCustomMarker(requireContext(), restaurant.youtuberProfile, restaurant.name)
                val marker = Marker().apply {
                    position = LatLng(restaurant.latitude, restaurant.longitude)
//                    captionText = restaurant.name // RestaurantName은 넣을 지 말지 고민 해보기
                    icon = OverlayImage.fromBitmap(markerBitmap)
                    map = naverMap

                    setOnClickListener {
                        bottomSheetHelper.showBottomSheetWithRestaurantInfo(restaurant)
                        true
                    }
                }
                markers[restaurant.uniqueKey] = marker
            }
        }
    }

    // 레스토랑 리스트를 로드
    private fun loadRestaurantList() {
        restaurantViewModel.loadRestaurantList(location.latitude, location.longitude)
    }

    // 인자로부터 위치 정보 받아오기
    private fun getLocationFromArguments(): Location {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(LOCATION_KEY, Location::class.java)
        } else {
            @Suppress("DEPRECATION")
            arguments?.getParcelable(LOCATION_KEY)
        } ?: Location("").apply {
            latitude = DEFAULT_LATITUDE
            longitude = DEFAULT_LONGITUDE
        }
    }

    // 생명주기 메서드 처리
    override fun onResume() {
        super.onResume()
        binding.mvHome.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.mvHome.onPause()
    }

    override fun onStart() {
        super.onStart()
        binding.mvHome.onStart()
    }

    override fun onStop() {
        super.onStop()
        binding.mvHome.onStop()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mvHome.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.mvHome.onDestroy()
    }

    companion object {
        private const val LOCATION_KEY = "location"
        private const val DEFAULT_LATITUDE = 37.566610
        private const val DEFAULT_LONGITUDE = 126.9783882

        fun newInstance(location: Location): MapFragment {
            return MapFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(LOCATION_KEY, location)
                }
            }
        }
    }
}

package com.knu.home

import android.location.Location
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.knu.common.view.viewBinding
import com.knu.home.entity.RestaurantEntity
import com.knu.retastylog.home.R
import com.knu.retastylog.home.databinding.MapFragmentBinding
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.NaverMap
import com.naver.maps.map.overlay.Marker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MapFragment : Fragment(R.layout.map_fragment) {

    private val binding by viewBinding(MapFragmentBinding::bind)
    private lateinit var naverMap: NaverMap
    private lateinit var location: Location

    private val restaurantViewModel: RestaurantViewModel by viewModels() // ViewModel 주입

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mvHome.onCreate(savedInstanceState)
        binding.mvHome.getMapAsync { map ->
            naverMap = map
            initializeMap()
            loadRestaurantList() // 레스토랑 리스트 로드
        }

        location = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(LOCATION_KEY, Location::class.java)
        } else {
            @Suppress("DEPRECATION")
            arguments?.getParcelable(LOCATION_KEY)
        } ?: Location("").apply {
            latitude = DEFAULT_LATITUDE
            longitude = DEFAULT_LONGITUDE
        }

        observeRestaurantList() // ViewModel 데이터 관찰
    }

    // 지도 초기화
    private fun initializeMap() {
        val cameraPosition = CameraPosition(
            LatLng(location.latitude, location.longitude), 15.0,
        )
        naverMap.cameraPosition = cameraPosition

        val marker = Marker().apply {
            position = LatLng(location.latitude, location.longitude)
            map = naverMap
        }
    }

    // ViewModel의 레스토랑 리스트 데이터를 구독
    private fun observeRestaurantList() {
        viewLifecycleOwner.lifecycleScope.launch {
            restaurantViewModel.restaurantList.collect { restaurantList ->
                updateMapMarkers(restaurantList)
            }
        }
    }

    // 레스토랑 리스트 조회
    private fun loadRestaurantList() {
        restaurantViewModel.loadRestaurantList(location.latitude, location.longitude)
    }

    // 지도에 마커 업데이트
    private fun updateMapMarkers(restaurantList: List<RestaurantEntity>) {
        restaurantList.forEach { restaurant ->
            val marker = Marker().apply {
                position = LatLng(restaurant.latitude, restaurant.longitude)
                captionText = restaurant.name
                map = naverMap
            }
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

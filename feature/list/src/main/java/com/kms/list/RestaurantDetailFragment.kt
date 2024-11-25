package com.kms.list

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import coil.load
import com.google.android.material.appbar.AppBarLayout
import com.kakao.sdk.share.ShareClient
import com.kakao.sdk.share.WebSharerClient
import com.kakao.sdk.template.model.Button
import com.kakao.sdk.template.model.Content
import com.kakao.sdk.template.model.FeedTemplate
import com.kakao.sdk.template.model.Link
import com.knu.common.view.viewBinding
import com.knu.designsystem.button.ActionButtonFactory
import com.knu.home.entity.RestaurantEntity
import com.knu.retastylog.list.BuildConfig
import com.knu.retastylog.list.R
import com.knu.retastylog.list.databinding.DetailRestaurantFragmentBinding
import com.knu.youtube.YouTubePlayerManager
import com.knu.youtube.YouTubeUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.UnsupportedEncodingException
import java.net.URLEncoder

@AndroidEntryPoint
class RestaurantDetailFragment : Fragment(R.layout.detail_restaurant_fragment) {

    private val binding by viewBinding(DetailRestaurantFragmentBinding::bind)
    private val sharedRestaurantViewModel: SharedRestaurantViewModel by activityViewModels()
    private lateinit var youTubePlayerManager: YouTubePlayerManager
    private var currentVideoId: String? = null
    private var selectedRestaurant: RestaurantEntity? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        youTubePlayerManager = YouTubePlayerManager(binding.ypvDetailRestaurant)
        initializeYouTubePlayer()
        setupToolbarScrollListener()
        observeSelectedRestaurant()
        setupShareButton()
        setupBackButton()
    }

    private fun initializeYouTubePlayer() {
        youTubePlayerManager.initialize { player ->
            currentVideoId?.let { videoId ->
                player.cueVideo(videoId, 0f)
            }
        }
    }

    private fun setupToolbarScrollListener() {
        binding.appBarLayoutAppBar.addOnOffsetChangedListener(
            AppBarLayout.OnOffsetChangedListener { _, verticalOffset ->
                val isCollapsed = binding.collapsingToolbarLayoutToolbar.height + verticalOffset <= binding.toolbar.height

                binding.tbDetailRestaurantBack.setImageResource(if (isCollapsed) R.drawable.ic_back_black else R.drawable.ic_back_white)
                binding.tbDetailRestaurantShare.setImageResource(if (isCollapsed) R.drawable.ic_share_black else R.drawable.ic_share_white)
            },
        )
    }

    private fun observeSelectedRestaurant() {
        viewLifecycleOwner.lifecycleScope.launch {
            sharedRestaurantViewModel.selectedRestaurant.collect { restaurant ->
                handleSelectedRestaurant(restaurant)
            }
        }
    }

    private fun handleSelectedRestaurant(restaurant: RestaurantEntity?) {
        selectedRestaurant = restaurant
        if (restaurant != null) {
            updateUI(restaurant)
            setupButtonContainer(restaurant)
            setupCopyAndMapIntentButtons(restaurant)
        } else {
            Log.e("RestaurantDetailFragment", "No restaurant selected.")
        }
    }

    private fun setupShareButton() {
        binding.tbDetailRestaurantShare.setOnClickListener {
            selectedRestaurant?.let { restaurant ->
                sendKakaoLink(restaurant)
            } ?: Toast.makeText(requireContext(), "레스토랑 정보가 없습니다.", Toast.LENGTH_SHORT).show()
        }
    }
    private fun setupBackButton() {
        binding.tbDetailRestaurantBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun updateUI(restaurant: RestaurantEntity) {
        val newVideoId = YouTubeUtils.extractYoutubeId(restaurant.youtuberLink)
        currentVideoId = newVideoId

        with(binding) {
            ivDetailRestaurantPicture.load(restaurant.representativeImage)
            tvDetailRestaurantYoutuberName.text = restaurant.youtuberName
            tvDetailRestaurantName.text = restaurant.name
            rbDetailRestaurantRating.setRating(restaurant.rating)
            tvDetailRestaurantRating.text = "${restaurant.rating} / 5"
            tvDetailRestaurantReview.text = "네이버 리뷰 ${restaurant.totalReviews}개"
            tvDetailRestaurantCategory.text = restaurant.category
            tvDetailRestaurantCallNumber.text = restaurant.phoneNumber ?: "연락처가 존재하지 않습니다."
            tvDetailRestaurantAddress.text = restaurant.address
            ivDetailRestaurantYoutubeThumnail.load("https://img.youtube.com/vi/$newVideoId/0.jpg")
            val mapPreviewUrl = generateStaticMapUrl(restaurant)
            ivDetailRestaurantMapPreview.load(mapPreviewUrl)
        }

        setupYoutubeIconClickListener(newVideoId)
    }

    private fun setupYoutubeIconClickListener(newVideoId: String) {
        binding.ivDetailRestaurantYoutubeIcon.setOnClickListener {
            playYoutubeVideo(newVideoId)
        }
    }

    private fun playYoutubeVideo(videoId: String) {
        binding.apply {
            ivDetailRestaurantYoutubeThumnail.visibility = View.GONE
            ivDetailRestaurantYoutubeIcon.visibility = View.GONE
            ypvDetailRestaurant.visibility = View.VISIBLE
        }

        youTubePlayerManager.playVideo(videoId)
    }

    private fun setupButtonContainer(restaurant: RestaurantEntity) {
        val buttonsContainer = binding.llDetailRestaurantButtonsContainer
        buttonsContainer.removeAllViews()

        val sendKakaoLinkAction = { sendKakaoLink(restaurant) }

        val buttonFactory = ActionButtonFactory(
            context = requireContext(),
            container = buttonsContainer,
            restaurant = restaurant,
            sendKakaoLinkAction = sendKakaoLinkAction,
        )

        buttonFactory.addButtons()
        if (buttonsContainer.childCount > 0) {
            val lastButton = buttonsContainer.getChildAt(buttonsContainer.childCount - 1)
            lastButton.visibility = View.GONE
        }
    }

    private fun sendKakaoLink(restaurant: RestaurantEntity) {
        val defaultFeed = FeedTemplate(
            content = Content(
                title = restaurant.name,
                description = restaurant.address,
                imageUrl = restaurant.representativeImage,
                link = Link(mobileWebUrl = restaurant.naverLink, webUrl = restaurant.naverLink),
            ),
            buttons = listOf(Button("자세히 보기", Link(mobileWebUrl = restaurant.naverLink, webUrl = restaurant.naverLink))),
        )

        if (ShareClient.instance.isKakaoTalkSharingAvailable(requireContext())) {
            ShareClient.instance.shareDefault(requireContext(), defaultFeed) { sharingResult, error ->
                if (error != null) {
                    Log.e("KakaoLink", "공유 실패", error)
                } else {
                    sharingResult?.intent?.let { requireContext().startActivity(it) }
                }
            }
        } else {
            val sharerUrl = WebSharerClient.instance.makeDefaultUrl(defaultFeed)
            openWebPage(sharerUrl.toString())
        }
    }

    private fun setupCopyAndMapIntentButtons(restaurant: RestaurantEntity) {
        binding.btnDetailRestaurantCopyAddress.setOnClickListener {
            val clipboard = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("Restaurant Address", restaurant.address)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(requireContext(), "주소가 복사되었습니다.", Toast.LENGTH_SHORT).show()
        }
        binding.btnDetailRestaurantIntentMap.setOnClickListener {
            openNaverMap(restaurant)
        }
    }

    private fun openNaverMap(restaurant: RestaurantEntity) {
        val naverLink = restaurant.naverLink
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(naverLink))
        if (intent.resolveActivity(requireContext().packageManager) != null) {
            startActivity(intent)
        } else {
            val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(naverLink))
            startActivity(webIntent)
        }
    }

    private fun openWebPage(url: String) {
        requireContext().startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }

    private fun generateStaticMapUrl(restaurant: RestaurantEntity): String {
        val clientId = BuildConfig.NAVER_MAP_CLIENT_ID
        val clientSecret = BuildConfig.NAVER_MAP_CLIENT_SECRET
        val encodedRestaurantName = try {
            URLEncoder.encode(restaurant.name, "UTF-8")
        } catch (e: UnsupportedEncodingException) {
            restaurant.name
        }

        val marker =
            "type:t|pos:${restaurant.longitude} ${restaurant.latitude}|color:0x80D7E0|label:$encodedRestaurantName"

        return "https://naveropenapi.apigw.ntruss.com/map-static/v2/raster?" +
                "w=600&h=300&center=${restaurant.longitude},${restaurant.latitude}&level=16&scale=2&markers=$marker" +
                "&X-NCP-APIGW-API-KEY-ID=$clientId&X-NCP-APIGW-API-KEY=$clientSecret"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        youTubePlayerManager.release()
    }

    companion object {
        private const val UNIQUE_KEY = "uniqueKey"

        fun newInstance(uniqueKey: String): RestaurantDetailFragment {
            return RestaurantDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(UNIQUE_KEY, uniqueKey)
                }
            }
        }
    }
}

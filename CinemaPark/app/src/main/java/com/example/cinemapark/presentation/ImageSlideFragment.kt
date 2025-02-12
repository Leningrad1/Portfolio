package com.example.cinemapark.presentation

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import com.example.cinemapark.R
import com.example.cinemapark.databinding.FragmentImageSlideBinding
import com.example.cinemapark.dataclass.gallery.Gallery
import com.example.cinemapark.dataclass.galleryframe.GalleryFrame
import com.example.cinemapark.dataclass.gallerypost.GalleryPost
import com.example.cinemapark.presentation.FilmPageFragment.Companion.OPEN_URL_GALLERY
import com.example.cinemapark.presentation.GalleryFragment.Companion.OPEN_URL_GALLERY_GAL
import com.example.cinemapark.presentation.recyclerviewadapter.gallery.ImageSlideAdapter
import com.example.cinemapark.presentation.recyclerviewadapter.galleryframe.ImageSlideFrameAdapter
import com.example.cinemapark.presentation.recyclerviewadapter.gallerypost.ImageSlidePostAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.crashlytics.FirebaseCrashlytics
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.relex.circleindicator.CircleIndicator

class ImageSlideFragment : Fragment() {

    private var _binding: FragmentImageSlideBinding? = null
    private val binding get() = _binding!!
    private var imagesModel: Gallery? = null
    private var imagesModelFrame: GalleryFrame? = null
    private var imagesModelPost: GalleryPost? = null
    private lateinit var viewPagerAdapter: ImageSlideAdapter
    private lateinit var viewPagerAdapterFrame: ImageSlideFrameAdapter
    private lateinit var viewPagerAdapterPost: ImageSlidePostAdapter
    private lateinit var indicator: CircleIndicator
    private lateinit var viewpager: ViewPager
    private val viewModel: MainViewModel by viewModels()
    private var currentPage: Int = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        // Inflate the layout for this fragment
        _binding = FragmentImageSlideBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            val bundle = this.arguments
            lifecycleScope.launch {
                val id = bundle!!.getInt(FilmPageFragment.URL_GALLERY)
                val url = bundle.getString(OPEN_URL_GALLERY)
                val urlGal = bundle.getString(OPEN_URL_GALLERY_GAL)
                viewModel.setValue(id)
                viewpager = view.findViewById(R.id.viewpager)
                val totalPageGallery = viewModel.repo.getGalleryId(id, 1).totalPages
                Log.d("slidePage", "$totalPageGallery")
                imagesModel = viewModel.repo.getGalleryId(id, currentPage)
                Log.d("slide", "${imagesModel}")
                imagesModelFrame = viewModel.repo.getGalleryIdFrame(id, currentPage)
                Log.d("slideFrame", "${imagesModelFrame}")
                imagesModelPost = viewModel.repo.getGalleryIdPost(id, currentPage)
                Log.d("slidePost", "${viewModel.chilBoolean}")
                imagesModel?.items?.forEach {
                    if (it.imageUrl == url) {
                        imagesModel?.items
                            .let {
                                Log.d("imagesModel", "${imagesModel}")
                                viewPagerAdapter = ImageSlideAdapter(requireContext(), it)
                                viewpager.adapter = viewPagerAdapter
                                indicator =
                                    requireView().findViewById(R.id.indicator) as CircleIndicator
                                indicator.setViewPager(viewpager)
                            }
                    }
                    val positionToOpen = imagesModel?.items?.indexOfFirst { it.imageUrl == url }
                    if (positionToOpen != -1) {
                        viewpager.currentItem = positionToOpen!!
                        viewpager.setCurrentItem(positionToOpen, true)
                    }
                    Log.d("positionToOpen", "${positionToOpen}")
                }
                imagesModel?.items?.forEach {
                    if (it.imageUrl == urlGal) {
                        imagesModel?.items
                            .let {
                                Log.d("imagesModel", "${imagesModel}")
                                viewPagerAdapter = ImageSlideAdapter(requireContext(), it)
                                viewpager.adapter = viewPagerAdapter
                                indicator =
                                    requireView().findViewById(R.id.indicator) as CircleIndicator
                                indicator.setViewPager(viewpager)
                            }
                    }
                    val positionToOpen = imagesModel?.items?.indexOfFirst { it.imageUrl == url }
                    if (positionToOpen != -1) {
                        viewpager.currentItem = positionToOpen!!
                        viewpager.setCurrentItem(positionToOpen, true)
                    }
                    Log.d("positionToOpen", "${positionToOpen}")
                }
                imagesModelFrame?.items?.forEach {
                    if (it.imageUrl == urlGal) {
                        imagesModelFrame?.items
                            .let {
                                Log.d("imagesModelFrame", "${imagesModelFrame}")
                                viewPagerAdapterFrame = ImageSlideFrameAdapter(requireContext(), it)
                                viewpager.adapter = viewPagerAdapterFrame
                                indicator =
                                    requireView().findViewById(R.id.indicator) as CircleIndicator
                                indicator.setViewPager(viewpager)
                            }
                    }
                    val positionToOpen =
                        imagesModelFrame?.items?.indexOfFirst { it.imageUrl == urlGal }
                    if (positionToOpen != -1) {
                        viewpager.currentItem = positionToOpen!!
                        viewpager.setCurrentItem(positionToOpen, true)
                    }
                }
                imagesModelPost?.items?.forEach {
                    if (it.imageUrl == urlGal) {
                        imagesModelPost?.items
                            .let {
                                Log.d("imagesModelPost", "${imagesModelPost}")
                                viewPagerAdapterPost = ImageSlidePostAdapter(requireContext(), it)
                                viewpager.adapter = viewPagerAdapterPost
                                indicator =
                                    requireView().findViewById(R.id.indicator) as CircleIndicator
                                indicator.setViewPager(viewpager)
                            }
                    }
                    val positionToOpen =
                        imagesModelPost?.items?.indexOfFirst { it.imageUrl == urlGal }
                    if (positionToOpen != -1) {
                        viewpager.currentItem = positionToOpen!!
                        viewpager.setCurrentItem(positionToOpen, true)
                    }
                }

                viewpager.addOnPageChangeListener(
                    object : ViewPager.OnPageChangeListener {
                        override fun onPageScrollStateChanged(state: Int) {
                            // При изменении состояния прокрутки
                        }

                        override fun onPageScrolled(
                            position: Int,
                            positionOffset: Float,
                            positionOffsetPixels: Int
                        ) {
                            // При каждом изменении состояния прокрутки
                            if (position == imagesModel!!.items.size - 1 ||
                                position == imagesModelFrame!!.items.size - 1 ||
                                position == imagesModelPost!!.items.size - 1
                            ) {
                                // Если пользователь достиг последней страницы, загружаем следующую
                                loadNextPage(id)
                            }
                        }

                        override fun onPageSelected(position: Int) {
                            // При изменении выбранной страницы
                        }
                    })

                binding.backGalFilmPage.setOnClickListener {
                    val fragment = ImageSlideFragment()
                    val bundle = Bundle()
                    bundle.putInt(ID_FILMS_GAL, id)
                    fragment.arguments = bundle
                    findNavController().navigate(
                        R.id.action_imageSlideFragment_to_filmPageFragment,
                        bundle
                    )
                }
            }
        } catch (e: Exception) {
            FirebaseCrashlytics.getInstance().recordException(e)
            sheetBottomError()
        }
    }
    private fun sheetBottomError() {
        viewLifecycleOwner.lifecycleScope.launch {
            val bottomSheetDialog = BottomSheetDialog(requireContext())
            val view = LayoutInflater.from(context).inflate(R.layout.item_botom_sheet_error, null)
            bottomSheetDialog.setContentView(view)
            delay(2000)
            bottomSheetDialog.show()
            val close: ImageView = view.findViewById(R.id.closeNameCollect)
            close.setOnClickListener {
                Log.d("close", "close")
                bottomSheetDialog.dismiss()
            }
        }
    }

    fun loadNextPage(id: Int) {
        lifecycleScope.launch {
            currentPage++
            val newImagesModel = viewModel.repo.getGalleryId(id, currentPage)
            val newImagesModelFrame = viewModel.repo.getGalleryIdFrame(id, currentPage)
            val newImagesModelPost = viewModel.repo.getGalleryIdPost(id, currentPage)
            newImagesModel.items.let {
                imagesModel?.items?.addAll(it) // Добавляем элементы из новой страницы к существующим
                viewPagerAdapter.notifyDataSetChanged() // Уведомляем адаптер о том, что данные изменились
                indicator.setViewPager(viewpager) //Добавляем индикатор
            }
            newImagesModelFrame.items.let {
                imagesModelFrame?.items?.addAll(it) // Добавляем элементы из новой страницы к существующим
                viewPagerAdapterFrame.notifyDataSetChanged() // Уведомляем адаптер о том, что данные изменились
                indicator.setViewPager(viewpager) //Добавляем индикатор
            }
            newImagesModelPost.items.let {
                imagesModelPost?.items?.addAll(it) // Добавляем элементы из новой страницы к существующим
                viewPagerAdapterPost.notifyDataSetChanged() // Уведомляем адаптер о том, что данные изменились
                indicator.setViewPager(viewpager) //Добавляем индикатор
            }
        }
        // Загрузка следующей страницы
    }

    override fun onDestroyView() {
        super.onDestroyView()
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    companion object {
        const val ID_FILMS_GAL = "id"
    }

}
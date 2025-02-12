package com.example.cinemapark.presentation

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
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cinemapark.R
import com.example.cinemapark.databinding.FragmentGalleryBinding
import com.example.cinemapark.presentation.FilmPageFragment.Companion.URL_GALLERY
import com.example.cinemapark.presentation.recyclerviewadapter.gallery.FilmPagingGalleryListAdapter
import com.example.cinemapark.presentation.recyclerviewadapter.gallery.SpaceItemDecoration
import com.example.cinemapark.presentation.recyclerviewadapter.galleryframe.FilmPagingGalleryFrameListAdapter
import com.example.cinemapark.presentation.recyclerviewadapter.gallerypost.FilmPagingGalleryPostListAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.chip.Chip
import com.google.firebase.crashlytics.FirebaseCrashlytics
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class GalleryFragment : Fragment() {
    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels()
    private val pageAdapterGallery = FilmPagingGalleryListAdapter(::onItemClick)
    private val pageAdapterGalleryPost = FilmPagingGalleryPostListAdapter(::onItemClick)
    private val pageAdapterGalleryFrame = FilmPagingGalleryFrameListAdapter(::onItemClick)
    private var idFilmGal = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            val bundle = this.arguments
            lifecycleScope.launch {
                val layoutManager =
                    GridLayoutManager(requireContext(), 2)
                layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return if (position % 3 == 2) {
                            2
                        } else {
                            1
                        }
                    }
                }
                binding.apply {
                    recyclerGallery.addItemDecoration(SpaceItemDecoration(10))
                    recyclerGallery.layoutManager = layoutManager
                    val id = bundle!!.getInt(ID_FILMS_ROLE)
                    idFilmGal = id
                    viewModel.setValue(id)
                    if (viewModel.chilBoolean != -1) {
                        val selectedChip = chipGroup.findViewById<Chip>(viewModel.chilBoolean)
                        selectedChip?.isChecked = true
                    }
                    chip1.isChecked = true
                    if (viewModel.chilBoolean == -1) {
                        recyclerGallery.adapter = pageAdapterGallery
                        viewModel.pageGalleryAll.onEach {
                            pageAdapterGallery.submitData(it)
                        }.launchIn(viewLifecycleOwner.lifecycleScope)
                    }
                    when (viewModel.chilBoolean) {
                        2131296919 -> {
                            Log.d("viewModel.count", "${viewModel.count}")
                            recyclerGallery.adapter = pageAdapterGallery
                            viewModel.pageGalleryAll.onEach {
                                pageAdapterGallery.submitData(it)
                            }.launchIn(viewLifecycleOwner.lifecycleScope)
                        }

                        2131296920 -> {
                            Log.d("viewModel.count2", "${viewModel.count}")
                            recyclerGallery.adapter = pageAdapterGalleryFrame
                            viewModel.pageGalleryFrameAll.onEach {
                                pageAdapterGalleryFrame.submitData(it)
                            }.launchIn(viewLifecycleOwner.lifecycleScope)
                        }

                        2131296921 -> {
                            Log.d("viewModel.count3", "${viewModel.count}")
                            recyclerGallery.adapter = pageAdapterGalleryPost
                            viewModel.pageGalleryPostAll.onEach {
                                pageAdapterGalleryPost.submitData(it)
                            }.launchIn(viewLifecycleOwner.lifecycleScope)
                        }
                    }
                    chip1.text = "Кадры ${viewModel.repo.getGalleryId(id, 1).total}"
                    chip2.text = "Со съемок ${viewModel.repo.getGalleryIdFrame(id, 1).total}"
                    chip3.text = "Постер ${viewModel.repo.getGalleryIdPost(id, 1).total}"

                    liftBack.setOnClickListener {
                        findNavController().navigate(
                            R.id.action_galleryFragment_to_filmPageFragment,
                            bundle
                        )
                    }
                    chipGroup.setOnCheckedChangeListener { group, checkedIds ->
                        val chip: Chip? = group.findViewById(checkedIds)
                        viewModel.chilBoolean = checkedIds
                        when (chip?.id) {
                            R.id.chip1 -> {
                                recyclerGallery.adapter = pageAdapterGallery
                                viewModel.pageGalleryAll.onEach {
                                    pageAdapterGallery.submitData(it)
                                }.launchIn(viewLifecycleOwner.lifecycleScope)
                                Log.d("chip1", "${checkedIds}")
                            }

                            R.id.chip2 -> {
                                recyclerGallery.adapter = pageAdapterGalleryFrame
                                viewModel.pageGalleryFrameAll.onEach {
                                    pageAdapterGalleryFrame.submitData(it)
                                }.launchIn(viewLifecycleOwner.lifecycleScope)
                                Log.d("chip2", "${checkedIds}")

                            }

                            R.id.chip3 -> {
                                recyclerGallery.adapter = pageAdapterGalleryPost
                                viewModel.pageGalleryPostAll.onEach {
                                    pageAdapterGalleryPost.submitData(it)
                                }.launchIn(viewLifecycleOwner.lifecycleScope)
                                Log.d("chip3", "${checkedIds}")

                            }
                        }
                    }
                    home.setOnClickListener {
                        findNavController().navigate(R.id.action_galleryFragment_to_homepageFragment)
                    }
                    search.setOnClickListener {
                        findNavController().navigate(R.id.action_galleryFragment_to_searchFragment)
                    }
                    profile.setOnClickListener {
                        findNavController().navigate(R.id.action_galleryFragment_to_profileFragment)
                    }
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
    private fun onItemClick(item: String) {
        lifecycleScope.launch {
            val fragment = GalleryFragment()
            val bundle = Bundle()
            bundle.putInt(URL_GALLERY, idFilmGal)
            bundle.putString(OPEN_URL_GALLERY_GAL, item)
            fragment.arguments = bundle
            findNavController().navigate(
                R.id.action_galleryFragment_to_imageSlideFragment,
                bundle
            )
        }
    }

    companion object {
        const val ID_FILMS_ROLE = "id"
        const val OPEN_URL_GALLERY_GAL = "url_open_gal"
    }
}
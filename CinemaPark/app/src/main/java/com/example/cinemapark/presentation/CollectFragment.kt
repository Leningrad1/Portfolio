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
import androidx.paging.PagingData
import com.example.cinemapark.R
import com.example.cinemapark.databinding.FragmentCollectBinding
import com.example.cinemapark.dataclass.filmpage.FilmPage
import com.example.cinemapark.presentation.ProfileFragment.Companion.ID_FILMS_COLLECT_LIST
import com.example.cinemapark.presentation.recyclerviewadapter.collect.FilmCollectProfileListFragmentAdapters
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.crashlytics.FirebaseCrashlytics
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class CollectFragment : Fragment() {
    private var _binding: FragmentCollectBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels()
    private var collectName = ""
    private var pageAdapterCollectFilm =
        FilmCollectProfileListFragmentAdapters(::onItemClickCollectFilm)
    private var mutableListCollectFilm = mutableListOf<Int>()
    private var mutableListCollect = mutableListOf<FilmPage>()
    private fun onItemClickCollectFilm(item: Int) {
        lifecycleScope.launch {
            val fragment = CollectFragment()
            val bundle = Bundle()
            bundle.putInt(ID_COLLECT, item)
            fragment.arguments = bundle
            findNavController().navigate(
                R.id.action_collectFragment_to_filmPageFragment,
                bundle
            )
            Log.d("bundleFilmPage, item", "$bundle, $item")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCollectBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = this.arguments
        try {
            binding.liftBack.setOnClickListener {
                findNavController().navigate(R.id.action_collectFragment_to_profileFragment)
            }
            binding.home.setOnClickListener {
                findNavController().navigate(R.id.action_collectFragment_to_homepageFragment)
            }
            binding.search.setOnClickListener {
                findNavController().navigate(R.id.action_collectFragment_to_searchFragment)
            }
            binding.profile.setOnClickListener {
                findNavController().navigate(R.id.action_collectFragment_to_profileFragment)
            }
            var nameCollectBundle = bundle!!.getString(ID_FILMS_COLLECT_LIST)
            collectName = nameCollectBundle.toString()
            binding.textViewNameListPage.text = collectName
            binding.listFilmRecycler.adapter = pageAdapterCollectFilm
            nameCollectBundle?.let { collect(it) }
        } catch (e: Exception) {
            FirebaseCrashlytics.getInstance().recordException(e)
            sheetBottomError()
        }

    }

    private fun collect(nameCollectBundle: String) {
        try {
            lifecycleScope.launch {
                viewModel.allCollectFilm.collect { list ->
                    mutableListCollectFilm.clear()
                    mutableListCollect.clear()
                    delay(1000)
                    Log.d("collectFilm1", "${list}")
                    list.forEach { collectItem ->
                        collectItem.movieList.forEach { movieItem ->
                            if (collectItem.collectFilm.collectFilm == nameCollectBundle) {
                                mutableListCollectFilm.add(movieItem.movieId)
                            }
                        }

                    }
                    val filmPages = mutableListCollectFilm.map { movieId ->
                        async { viewModel.repo.getFilmPageId(movieId) }
                    }.awaitAll()

                    mutableListCollect.addAll(filmPages)

                    val pagingData: PagingData<FilmPage> =
                        PagingData.from(filmPages)
                    pageAdapterCollectFilm.submitData(lifecycle, pagingData)
                }
            }
        } catch (e: Exception) {
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
    companion object {
        const val ID_COLLECT = "id film coll"
    }
}

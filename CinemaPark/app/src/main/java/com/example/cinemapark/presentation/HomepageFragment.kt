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
import com.example.cinemapark.R
import com.example.cinemapark.databinding.FragmentHomepageBinding
import com.example.cinemapark.presentation.recyclerviewadapter.action.FilmUsaActionListAdapter
import com.example.cinemapark.presentation.recyclerviewadapter.drame.FilmPagingDrameFranceListAdapter
import com.example.cinemapark.presentation.recyclerviewadapter.popular.FilmPagingPopularListAdapter
import com.example.cinemapark.presentation.recyclerviewadapter.premier.FilmPagingPremierListAdapter
import com.example.cinemapark.presentation.recyclerviewadapter.serias.FilmPagingSeriasListAdapter
import com.example.cinemapark.presentation.recyclerviewadapter.top.FilmTopPagingListAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.crashlytics.FirebaseCrashlytics
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomepageFragment : Fragment() {
    private var _binding: FragmentHomepageBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels()
    private var pageAdapterPremier = FilmPagingPremierListAdapter(::onItemClick)
    private val pageAdapterPopular = FilmPagingPopularListAdapter(::onItemClick)
    private val pageAdapterUsaMil = FilmUsaActionListAdapter(::onItemClick)
    private val pageAdapterTop = FilmTopPagingListAdapter(::onItemClick)
    private val pageAdapterDrame = FilmPagingDrameFranceListAdapter(::onItemClick)
    private val pageAdapterSerias = FilmPagingSeriasListAdapter(::onItemClick)
    private var nameTypeFilm = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomepageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            binding.recyclerViewPremier.adapter = pageAdapterPremier
            binding.recyclerViewPopular.adapter = pageAdapterPopular
            binding.recyclerViewAction.adapter = pageAdapterUsaMil
            binding.recyclerViewTop.adapter = pageAdapterTop
            binding.recyclerViewDrame.adapter = pageAdapterDrame
            binding.recyclerViewSeries.adapter = pageAdapterSerias
            lifecycleScope.launch {
                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.getCombinedPremierFlow().collectLatest { pagingData ->
                        pageAdapterPremier.submitData(pagingData)
                    }
                }
                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.getCombinedPopularFlow().collectLatest { pagingData ->
                        pageAdapterPopular.submitData(pagingData)
                    }
                }
                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.getCombinedUsaMilitaryFlow().collectLatest { pagingData ->
                        pageAdapterUsaMil.submitData(pagingData)
                    }
                }
                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.getCombinedTopFilmFlow().collectLatest { pagingData ->
                        pageAdapterTop.submitData(pagingData)
                    }
                }
                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.getCombinedDrameFilmFlow().collectLatest { pagingData ->
                        pageAdapterDrame.submitData(pagingData)
                    }
                }
                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.getCombinedSeriesFlow().collectLatest { pagingData ->
                        pageAdapterSerias.submitData(pagingData)
                    }
                }

            }
            binding.allPremier.setOnClickListener {
                nameTypeFilm = 1
                onClick(nameTypeFilm)
            }
            binding.allPopular.setOnClickListener {
                nameTypeFilm = 2
                onClick(nameTypeFilm)
            }
            binding.allAction.setOnClickListener {
                nameTypeFilm = 3
                onClick(nameTypeFilm)
            }
            binding.allTop.setOnClickListener {
                nameTypeFilm = 4
                onClick(nameTypeFilm)
            }
            binding.allDrame.setOnClickListener {
                nameTypeFilm = 5
                onClick(nameTypeFilm)
            }
            binding.allSeries.setOnClickListener {
                nameTypeFilm = 6
                onClick(nameTypeFilm)
            }
            binding.search.setOnClickListener {
                findNavController().navigate(R.id.action_homepageFragment_to_searchFragment)
            }
            binding.profile.setOnClickListener {
                findNavController().navigate(R.id.action_homepageFragment_to_profileFragment)
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

    private fun onClick(it: Int) {
        lifecycleScope.launch {
            val fragment = HomepageFragment()
            val bundle = Bundle()
            bundle.putInt(TYPE_FILMS, it)
            fragment.arguments = bundle
            findNavController().navigate(
                R.id.action_homepageFragment_to_listPageFragment,
                bundle
            )
        }
    }

    private fun onItemClick(item: Int) {
        lifecycleScope.launch {
            val fragment = HomepageFragment()
            val bundle = Bundle()
            bundle.putInt(ID_FILMS, item)
            fragment.arguments = bundle
            findNavController().navigate(
                R.id.action_homepageFragment_to_filmPageFragment,
                bundle
            )
        }
    }

    companion object {
        const val TYPE_FILMS = "name"
        const val ID_FILMS = "id"
    }
}
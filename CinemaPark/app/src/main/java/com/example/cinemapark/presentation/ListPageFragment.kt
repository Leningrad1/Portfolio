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
import com.example.cinemapark.databinding.FragmentListPageBinding
import com.example.cinemapark.presentation.HomepageFragment.Companion.TYPE_FILMS
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

class ListPageFragment : Fragment() {

    private var _binding: FragmentListPageBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels()
    private val pageAdapterPremier = FilmPagingPremierListAdapter(::onItemClick)
    private val pageAdapterPopular = FilmPagingPopularListAdapter(::onItemClick)
    private val pageAdapterUsaMil = FilmUsaActionListAdapter(::onItemClick)
    private val pageAdapterTop = FilmTopPagingListAdapter(::onItemClick)
    private val pageAdapterDrame = FilmPagingDrameFranceListAdapter(::onItemClick)
    private val pageAdapterSerias = FilmPagingSeriasListAdapter(::onItemClick)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListPageBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            binding.liftBack.setOnClickListener {
                findNavController().navigate(R.id.action_listPageFragment_to_homepageFragment)
            }
            binding.home.setOnClickListener {
                findNavController().navigate(R.id.action_listPageFragment_to_homepageFragment)
            }
            binding.search.setOnClickListener {
                findNavController().navigate(R.id.action_listPageFragment_to_searchFragment)
            }
            binding.profile.setOnClickListener {
                findNavController().navigate(R.id.action_listPageFragment_to_profileFragment)
            }
            val bundle = this.arguments
            lifecycleScope.launch {
                if (bundle != null) {
                    when (bundle.getInt(TYPE_FILMS)) {
                        1 -> {
                            binding.textViewNameListPage.text = "Премьеры"
                            viewLifecycleOwner.lifecycleScope.launch {
                                viewModel.getCombinedPremierFlow().collectLatest { pagingData ->
                                    pageAdapterPremier.submitData(pagingData)
                                }
                            }
                            binding.listFilmRecycler.adapter = pageAdapterPremier
                        }

                        2 -> {
                            binding.textViewNameListPage.text = "Популярное"
                            viewLifecycleOwner.lifecycleScope.launch {
                                viewModel.getCombinedPopularFlow().collectLatest { pagingData ->
                                    pageAdapterPopular.submitData(pagingData)
                                }
                            }
                            binding.listFilmRecycler.adapter = pageAdapterPopular
                        }

                        3 -> {
                            binding.textViewNameListPage.text = "Боевики США"
                            viewLifecycleOwner.lifecycleScope.launch {
                                viewModel.getCombinedUsaMilitaryFlow().collectLatest { pagingData ->
                                    pageAdapterUsaMil.submitData(pagingData)
                                }
                            }
                            binding.listFilmRecycler.adapter = pageAdapterUsaMil
                        }

                        4 -> {
                            binding.textViewNameListPage.text = "ТОП 250"
                            viewLifecycleOwner.lifecycleScope.launch {
                                viewModel.getCombinedTopFilmFlow().collectLatest { pagingData ->
                                    pageAdapterTop.submitData(pagingData)
                                }
                            }
                            binding.listFilmRecycler.adapter = pageAdapterTop
                        }

                        5 -> {
                            binding.textViewNameListPage.text = "Драмы Франции"
                            viewLifecycleOwner.lifecycleScope.launch {
                                viewModel.getCombinedDrameFilmFlow().collectLatest { pagingData ->
                                    pageAdapterDrame.submitData(pagingData)
                                }
                            }
                            binding.listFilmRecycler.adapter = pageAdapterDrame
                        }

                        6 -> {
                            binding.textViewNameListPage.text = "Сериалы"
                            viewLifecycleOwner.lifecycleScope.launch {
                                viewModel.getCombinedSeriesFlow().collectLatest { pagingData ->
                                    pageAdapterSerias.submitData(pagingData)
                                }
                            }
                            binding.listFilmRecycler.adapter = pageAdapterSerias
                        }
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

    private fun onItemClick(item: Int) {
        lifecycleScope.launch {
            val fragment = ListPageFragment()
            val bundle = Bundle()
            bundle.putInt(ID_FILMS_LIST_PAGE, item)
            Log.d("itemListPage", "${item}")
            fragment.arguments = bundle
            findNavController().navigate(
                R.id.action_listPageFragment_to_filmPageFragment,
                bundle
            )
        }
    }

    companion object {
        const val ID_FILMS_LIST_PAGE = "id film"
    }

}

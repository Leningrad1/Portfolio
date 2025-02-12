package com.example.cinemapark.presentation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.cinemapark.R
import com.example.cinemapark.data.retrofit.FilmRepository
import com.example.cinemapark.databinding.FragmentSearchBinding
import com.example.cinemapark.presentation.recyclerviewadapter.searchfilmparam.SearchFilmParamAdapter
import com.example.cinemapark.presentation.recyclerviewadapter.searchkeyword.SearchKeyWordAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.crashlytics.FirebaseCrashlytics
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels()
    private val pageAdapterSearchKeyWord = SearchKeyWordAdapter(::onItemClick)
    private val pageAdapterSearchParam = SearchFilmParamAdapter(::onItemClickSearchParam)
    val repo = FilmRepository()

    private fun onItemClick(item: Int) {
        lifecycleScope.launch {
            val fragment = SearchFragment()
            val bundle = Bundle()
            bundle.putInt(ITEM_ID, item)
            fragment.arguments = bundle
            findNavController().navigate(
                R.id.action_searchFragment_to_filmPageFragment,
                bundle
            )
        }
    }

    private fun onItemClickSearchParam(i: Int) {
        TODO("Not yet implemented")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            val bundle = this.arguments
            lifecycleScope.launch {
                val id = bundle?.getInt(SearchAllInfoFragment.ID)
                Log.d("searchCountry", "${id}")
                val idCountry = bundle?.getInt(SearchAllInfoFragment.COUNTRY)
                idCountry?.let { viewModel.addCountry(it) }
                Log.d("searchCountry", "${idCountry}")
                val idGenre = bundle?.getInt(SearchAllInfoFragment.GENRE)
                idGenre?.let { viewModel.addGenre(it) }
                Log.d("searchidGenre", "${idGenre}")
                val changeSort = bundle?.getString(SearchAllInfoFragment.ORDER)
                Log.d("searchchangeSort", "${changeSort}")
                changeSort?.let { viewModel.addOrder(it) }
                val changeFilms = bundle?.getString(SearchAllInfoFragment.TYPE)
                Log.d("searchchangeFilms", "${changeFilms}")
                changeFilms?.let { viewModel.addType(it) }
                val minSlider = bundle?.getInt(SearchAllInfoFragment.RATING_FROM)
                Log.d("searchminSlider", "${minSlider}")
                minSlider?.let { viewModel.addRatingFrom(it) }
                val maxSlider = bundle?.getInt(SearchAllInfoFragment.RATING_TO)
                Log.d("searchmmaxSlider", "${maxSlider}")
                maxSlider?.let { viewModel.addRatingTo(it) }
                val minYear = bundle?.getInt(SearchAllInfoFragment.YEAR_FROM)
                Log.d("searchminYear", "${minYear}")
                minYear?.let { viewModel.addYearFrom(it) }
                val maxYear = bundle?.getInt(SearchAllInfoFragment.YEAR_TO)
                Log.d("searchmaxYear", "${maxYear}")
                maxYear?.let { viewModel.addYearTo(it) }
                binding.recyclerSearch.visibility = View.VISIBLE
                binding.textSearch.addTextChangedListener { text ->
                    val editText = text.toString()
                    Log.d("editText1", "$editText")
                    binding.recyclerSearch.adapter = pageAdapterSearchKeyWord
                    update(editText)
                }  
                if (id == 1) {
                    Log.d("1", "$id")
                    binding.recyclerSearch.adapter = pageAdapterSearchParam
                    Log.d("2", "$id")
                    viewModel.pageSearchParam.onEach {
                        pageAdapterSearchParam.submitData(it)
                        Log.d("viewModel.pageSearchParam", "$it")
                    }.launchIn(viewLifecycleOwner.lifecycleScope)
                    Log.d("3", "$id")
                }
                binding.imageParam.setOnClickListener {
                    findNavController().navigate(R.id.action_searchFragment_to_searchAllInfoFragment)
                }
                binding.home.setOnClickListener {
                    findNavController().navigate(R.id.action_searchFragment_to_homepageFragment)
                }
                binding.profile.setOnClickListener {
                    findNavController().navigate(R.id.action_searchFragment_to_profileFragment)
                }
            }
        } catch (e: Exception) {
            FirebaseCrashlytics.getInstance().recordException(e)
            sheetBottomError()
        }
    }

    private fun update(editText: String) {
        try {
            lifecycleScope.launch {
                viewModel.setValueSearchKeyWord(editText)
                Log.d("editText3", "$editText")
                if (repo.getSearchKeyWord(editText, 1).pagesCount != 0) {
                    binding.recyclerSearch.visibility = View.VISIBLE
                    binding.textNotFound.visibility = View.GONE
                    viewModel.pageSearchKeyWord.onEach {
                        pageAdapterSearchKeyWord.submitData(it)
                    }.launchIn(viewLifecycleOwner.lifecycleScope)
                } else {
                    binding.recyclerSearch.visibility = View.GONE
                    binding.textNotFound.visibility = View.VISIBLE
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

    companion object {
        const val ITEM_ID = "id"
    }
}
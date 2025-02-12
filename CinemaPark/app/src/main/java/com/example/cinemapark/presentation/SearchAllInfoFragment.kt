package com.example.cinemapark.presentation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cinemapark.R
import com.example.cinemapark.databinding.FragmentSearchAllInfoBinding
import com.example.cinemapark.dataclass.genrecountry.Country
import com.example.cinemapark.dataclass.genrecountry.Genre
import com.example.cinemapark.presentation.recyclerviewadapter.genrecountry.GenreAdapters
import com.example.cinemapark.presentation.recyclerviewadapter.genrecountry.GenreCountryAdapters
import com.example.cinemapark.presentation.recyclerviewadapter.searchyear.SearchYearSecondAdapters
import com.example.cinemapark.presentation.recyclerviewadapter.searchyear.SearchYeardapters
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.crashlytics.FirebaseCrashlytics
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchAllInfoFragment : Fragment() {
    private var _binding: FragmentSearchAllInfoBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels()
    private var changeFilms: String = ""
    private val pageAdapterGenreCountry = GenreCountryAdapters(::onItemClick)
    private val pageAdapterGenre = GenreAdapters(::onClick)
    private var idCountry = 0
    private var nameCountry = ""
    private var idGenre = 0
    private var nameGenre = ""
    private var minSlider = 0
    private var maxSlider = 10
    private var changeSort: String = ""
    private var minYear: Int = 1895
    private var maxYear: Int = 2024
    private var positionToScroll = 120
    private var positionToScrollSecond = 120
    private var idSearch = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchAllInfoBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            lifecycleScope.launch {
                binding.linearSearchAll.visibility = View.VISIBLE
                chipAllFilmsSerials()
                changeCountry()
                changeGenre()
                showYear()
                sliderRang()
                toggleOrder()
                binding.yearButton.setOnClickListener {
                    if (minYear <= maxYear) {
                        binding.linearSearchAll.visibility = View.VISIBLE
                        binding.searchYear.visibility = View.GONE
                        binding.yearChoise.text = "с $minYear по $maxYear"
                    } else {
                        showToast(
                            "Значение минимального года не должно быть больше значения максимального года. " +
                                    "Введите значения еще раз!"
                        )
                    }
                }
                binding.leftBackSearch.setOnClickListener {
                    val fragment = SearchAllInfoFragment()
                    val bundle = Bundle()
                    if (idCountry != 0 && idGenre != 0) {
                        idSearch = 1
                        Log.d("idSearch", "$idSearch")
                    }
                    bundle.putInt(ID, idSearch)
                    bundle.putInt(COUNTRY, idCountry)
                    bundle.putInt(GENRE, idGenre)
                    bundle.putString(ORDER, changeSort)
                    bundle.putString(TYPE, changeFilms)
                    bundle.putInt(RATING_FROM, minSlider)
                    bundle.putInt(RATING_TO, maxSlider)
                    bundle.putInt(YEAR_FROM, minYear)
                    bundle.putInt(YEAR_TO, maxYear)
                    fragment.arguments = bundle
                    findNavController().navigate(
                        R.id.action_searchAllInfoFragment_to_searchFragment,
                        bundle
                    )
                }
            }
        } catch (e: Exception) {
            FirebaseCrashlytics.getInstance().recordException(e)
            sheetBottomError()
        }

    }

    private fun chipAllFilmsSerials() {
        binding.apply {
            buttonGroupSearch.addOnButtonCheckedListener { group, checkedId, isChecked ->
                if (isChecked) {
                    when (checkedId) {
                        R.id.chip_all -> {
                            changeFilms = "ALL"
                            showToast(changeFilms)
                        }

                        R.id.button_films -> {
                            changeFilms = "FILM"
                            showToast(changeFilms)
                        }

                        R.id.button_serials -> {
                            changeFilms = "TV_SERIES"
                            showToast(changeFilms)
                        }
                    }
                } else {
                    if (group.checkedButtonId == View.NO_ID) {
                        changeFilms = "Nothing"
                        showToast(changeFilms)
                    }
                }
            }
        }
    }

    private fun changeCountry() {
        lifecycleScope.launch {
            try {
                binding.apply {
                    country.setOnClickListener {
                        linearSearchAll.visibility = View.GONE
                        searchCountry.visibility = View.VISIBLE
                    }
                    searchLeftbackCountry.setOnClickListener {
                        binding.linearSearchAll.visibility = View.VISIBLE
                        binding.searchCountry.visibility = View.GONE
                    }
                    viewModel.repo.getGenreCountry().countries.forEach {
                        viewModel.countryList.add(it)
                    }
                    recyclerSearchCountry.adapter = pageAdapterGenreCountry
                    searchViewCountry.setOnQueryTextListener(object :
                        SearchView.OnQueryTextListener {
                        override fun onQueryTextSubmit(query: String?): Boolean {
                            Log.d("country", "country")
                            return false
                        }

                        override fun onQueryTextChange(newText: String?): Boolean {
                            lifecycleScope.launch {
                                try {
                                    viewModel.countryList.forEach {
                                        if (it.country == newText) {
                                            Log.d("countryTrue", "${it.country}")
                                            viewModel.countryListAdd.add(it)
                                            Log.d("countryList", "${it}")
                                        }
                                    }
                                    Log.d("countryListAdd", "${viewModel.countryListAdd}")
                                    Log.d("countryListAd", "${viewModel.countryList}")
                                    val pagingData: PagingData<Country> =
                                        PagingData.from(
                                            viewModel.countryListAdd.subList(
                                                0,
                                                viewModel.countryListAdd.size
                                            )
                                        )
                                    pageAdapterGenreCountry.submitData(lifecycle, pagingData)
                                } catch (e: Exception) {
                                    FirebaseCrashlytics.getInstance().recordException(e)
                                    sheetBottomError()
                                }
                            }
                            return false
                        }
                    })

                    searchLeftbackCountry.setOnClickListener {
                        linearSearchAll.visibility = View.VISIBLE
                        searchCountry.visibility = View.GONE
                    }
                    Log.d("genre", "${viewModel.repo.getGenreCountry()}")
                }
            } catch (e: Exception) {
                FirebaseCrashlytics.getInstance().recordException(e)
                sheetBottomError()
            }
        }
    }

    private fun changeGenre() {
        lifecycleScope.launch {
            try {
                binding.apply {
                    genre.setOnClickListener {
                        linearSearchAll.visibility = View.GONE
                        searchGanre.visibility = View.VISIBLE
                    }
                    searchLeftbackGanre.setOnClickListener {
                        binding.linearSearchAll.visibility = View.VISIBLE
                        binding.searchGanre.visibility = View.GONE
                    }
                    viewModel.repo.getGenreCountry().genres.forEach {
                        viewModel.genreList.add(it)
                    }
                    recyclerSearchGanre.adapter = pageAdapterGenre
                    searchViewGanre.setOnQueryTextListener(object :
                        SearchView.OnQueryTextListener {
                        override fun onQueryTextSubmit(query: String?): Boolean {
                            Log.d("country", "country")
                            return false
                        }

                        override fun onQueryTextChange(newText: String?): Boolean {
                            lifecycleScope.launch {
                                try {
                                    viewModel.genreList.forEach {
                                        if (it.genre == newText) {
                                            Log.d("countryTrue", "${it.genre}")
                                            viewModel.genreListAdd.add(it)
                                            Log.d("countryList", "$it")
                                        }
                                    }
                                    Log.d("genreListAdd", "${viewModel.genreListAdd}")
                                    Log.d("genreListAd", "${viewModel.genreList}")
                                    val pagingData: PagingData<Genre> =
                                        PagingData.from(
                                            viewModel.genreListAdd.subList(
                                                0,
                                                viewModel.genreListAdd.size
                                            )
                                        )
                                    pageAdapterGenre.submitData(lifecycle, pagingData)
                                } catch (e: Exception) {
                                    FirebaseCrashlytics.getInstance().recordException(e)
                                    sheetBottomError()
                                }
                            }
                            return false
                        }
                    })

                    searchLeftbackCountry.setOnClickListener {
                        linearSearchAll.visibility = View.VISIBLE
                        searchCountry.visibility = View.GONE
                    }
                    Log.d("genre", "${viewModel.repo.getGenreCountry()}")
                }
            } catch (e: Exception) {
                FirebaseCrashlytics.getInstance().recordException(e)
                sheetBottomError()
            }
        }
    }

    private fun sliderRang() {
        try {
            lifecycleScope.launch {
                binding.apply {
                    sliderRange.addOnChangeListener { slider, value, fromUser ->
                        if (fromUser) {
                            sliderMin.text = slider.values[0].toInt().toString()
                            minSlider = slider.values[0].toInt()
                            sliderMax.text = slider.values[1].toInt().toString()
                            maxSlider = slider.values[1].toInt()
                            if (minSlider == 0 && maxSlider == 10) {
                                rangChoise.text = "Любой"
                            } else {
                                rangChoise.text = "$minSlider - $maxSlider"
                            }
                        }
                    }

                }
            }
        } catch (e: Exception) {
            FirebaseCrashlytics.getInstance().recordException(e)
            sheetBottomError()
        }
    }

    private fun toggleOrder() {
        try {
            binding.apply {
                chipGroupSort.addOnButtonCheckedListener { group, checkedId, isChecked ->
                    if (isChecked) {
                        when (checkedId) {
                            R.id.chip_data -> {
                                changeSort = "YEAR"
                                showToast(changeSort)
                            }

                            R.id.chip_pop -> {
                                changeSort = "NUM_VOTE"
                                showToast(changeSort)
                            }

                            R.id.chip_rang -> {
                                changeSort = "RATING"
                                showToast(changeSort)
                            }
                        }
                    } else {
                        if (group.checkedButtonId == View.NO_ID) {
                            changeSort = "Nothing"
                            showToast(changeSort)
                        }
                    }
                }
            }
        } catch (e: Exception) {
            FirebaseCrashlytics.getInstance().recordException(e)
            sheetBottomError()
        }
    }

    private fun showToast(str: String) {
        Toast.makeText(requireContext(), str, Toast.LENGTH_SHORT).show()
    }

    private fun showYear() {
        try {
            lifecycleScope.launch {
                binding.apply {
                    year.setOnClickListener {
                        linearSearchAll.visibility = View.GONE
                        searchYear.visibility = View.VISIBLE
                    }
                    searchLeftbackYear.setOnClickListener {
                        linearSearchAll.visibility = View.VISIBLE
                        searchYear.visibility = View.GONE
                        minYear = 0
                        maxYear = 0
                    }
                    val yearFirstAdapter = SearchYeardapters(::onClickSearch, viewModel.listYear)
                    val layoutManager =
                        GridLayoutManager(requireContext(), 3)
                    dateRecyclerviewSearch.layoutManager = layoutManager
                    dateRecyclerviewSearch.adapter = yearFirstAdapter
                    val yearSecondAdapter =
                        SearchYearSecondAdapters(::onClickSearchSecond, viewModel.listYear)
                    val layoutManagerSecond =
                        GridLayoutManager(requireContext(), 3)
                    dateRecyclerview.layoutManager = layoutManagerSecond
                    dateRecyclerview.adapter = yearSecondAdapter
                    delay(1000)
                    dateRecyclerviewSearch.smoothScrollToPosition(positionToScroll)
                    dateRecyclerview.smoothScrollToPosition(positionToScrollSecond)
                    backYear.setOnClickListener {
                        positionToScroll -= 10
                        dateRecyclerviewSearch.smoothScrollToPosition(positionToScroll)
                    }
                    forwardYear.setOnClickListener {
                        positionToScroll += 10
                        dateRecyclerviewSearch.smoothScrollToPosition(positionToScroll)
                    }
                    backYearSearch.setOnClickListener {
                        positionToScrollSecond -= 10
                        dateRecyclerview.smoothScrollToPosition(positionToScrollSecond)
                    }
                    forwardYearSearch.setOnClickListener {
                        positionToScrollSecond += 10
                        dateRecyclerview.smoothScrollToPosition(positionToScrollSecond)
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

    private fun onItemClick(s: Int?) {
        idCountry = s!!
        Log.d("idCountry", "$idCountry")
        binding.linearSearchAll.visibility = View.VISIBLE
        binding.searchCountry.visibility = View.GONE
        viewModel.countryList.forEach {
            if (it.id == idCountry) {
                nameCountry = it.country
                binding.countryChoise.text = nameCountry
            }
        }
        showToast(nameCountry)
        viewModel.countryListAdd.clear()
    }

    private fun onClick(s: Int?) {
        idGenre = s!!
        Log.d("idGenre", "$idGenre")
        binding.linearSearchAll.visibility = View.VISIBLE
        binding.searchGanre.visibility = View.GONE
        viewModel.genreList.forEach {
            if (it.id == idGenre) {
                nameGenre = it.genre
                binding.genreChoise.text = nameGenre
            }
        }
        showToast(nameGenre)
        viewModel.genreListAdd.clear()
    }

    private fun onClickSearch(itemMinYear: String?) {
        lifecycleScope.launch {
            binding.apply {
                minYear = itemMinYear?.toInt() ?: 0
                yearSearch.text = "$minYear - $maxYear"
                yearSearchData.text = "$minYear - $maxYear"
                showToast(minYear.toString())
            }
        }
    }

    private fun onClickSearchSecond(itemMaxYear: String?) {
        lifecycleScope.launch {
            binding.apply {
                maxYear = itemMaxYear?.toInt() ?: 0
                yearSearch.text = "$minYear - $maxYear"
                yearSearchData.text = "$minYear - $maxYear"
                showToast(maxYear.toString())
            }
        }
    }

    companion object {
        const val ID = "id"
        const val COUNTRY = "country"
        const val GENRE = "genre"
        const val ORDER = "order"
        const val TYPE = "type"
        const val RATING_FROM = "rating_from"
        const val RATING_TO = "rating_to"
        const val YEAR_FROM = "year_from"
        const val YEAR_TO = "year_to"
    }
}

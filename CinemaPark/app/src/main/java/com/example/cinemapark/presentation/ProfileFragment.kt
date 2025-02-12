package com.example.cinemapark.presentation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import androidx.recyclerview.widget.ConcatAdapter
import com.example.cinemapark.R
import com.example.cinemapark.databinding.FragmentProfileBinding
import com.example.cinemapark.dataclass.dbcollectfilm.CollectFilm
import com.example.cinemapark.dataclass.dbcollectfilm.FilmsWithCollection
import com.example.cinemapark.dataclass.filmpage.FilmPage
import com.example.cinemapark.presentation.recyclerviewadapter.collect.FilmCollectProfileListAdapters
import com.example.cinemapark.presentation.recyclerviewadapter.concatadapter.FooterConcatAdapter
import com.example.cinemapark.presentation.recyclerviewadapter.concatadapter.FooterConcatFavouriteAdapter
import com.example.cinemapark.presentation.recyclerviewadapter.favoritefilm.FilmPagingFavouriteFilmAdapter
import com.example.cinemapark.presentation.recyclerviewadapter.seefilm.FilmPagingSeeFilmAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.crashlytics.FirebaseCrashlytics
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Suppress("UNREACHABLE_CODE")
class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels()
    private var mutableListSeeFilm = mutableListOf<Int>()
    private var mutableListFavouriteFilm = mutableListOf<Int>()
    private var mutableListCollectFilm = mutableListOf<String>()
    private var mutableListSeeFilmPaging = mutableListOf<FilmPage>()
    private var mutableListFavouriteFilmPaging = mutableListOf<FilmPage>()
    private var pageAdapterFavouriteFilm =
        FilmPagingFavouriteFilmAdapter(::onItemClickFavouriteFilm)
    private var pageAdapterSeeFilm = FilmPagingSeeFilmAdapter(::onItemClickSeeFilm)
    private var listCollect = mutableListOf("Русское кино", "Хочу посмотреть", "Любимое")
    private var pageAdapterCollectFilm =
        FilmCollectProfileListAdapters(::onItemClickCollectFilm)
    private var pageAdapterCollectFilmFooter =
        FooterConcatAdapter(::onItemClickSeeFilmFooter, "Очистить \n историю")
    private var pageAdapterFavouriteFilmFooter =
        FooterConcatFavouriteAdapter(::onItemClickFavouriteFilmFooter, "Очистить \n историю")

    private fun onItemClickSeeFilmFooter(item: String) {
        viewLifecycleOwner.lifecycleScope.launch {
            Log.d(
                "mutableListSeeFilmGet", " " +
                        "${viewModel.allUserMovie.value}"
            )
            viewModel.allUserMovie.collect {
                it.forEach { list ->
                    viewModel.update(
                        userId = list.id, movieId = list.movieId, likeFilm = list.likeFilm,
                        favoritesFilm = list.favoritesFilm, seeFilm = false
                    )
                }
                mutableListSeeFilm.clear()
                mutableListSeeFilmPaging.clear()
                mutableListSeeFilm.forEach {
                    mutableListSeeFilmPaging.add(viewModel.repo.getFilmPageId(it))
                    Log.d("mutableListSeeFilmGet", "${viewModel.repo.getFilmPageId(it)}")
                }

                val pagingData: PagingData<FilmPage> =
                    PagingData.from(mutableListSeeFilmPaging)
                Log.d("mutableListSeeFilmGet", "${pagingData}")
                pageAdapterSeeFilm.submitData(lifecycle, pagingData)
            }
        }
    }

    private fun onItemClickFavouriteFilmFooter(item: String) {
        viewLifecycleOwner.lifecycleScope.launch {
            Log.d("delete", "delete")
            viewModel.allUserMovie.collect {
                it.forEach { list ->
                    viewModel.update(
                        userId = list.id, movieId = list.movieId, likeFilm = list.likeFilm,
                        favoritesFilm = false, seeFilm = list.seeFilm
                    )
                }
                mutableListFavouriteFilm.clear()
                mutableListFavouriteFilmPaging.clear()
                mutableListFavouriteFilm.forEach {
                    mutableListFavouriteFilmPaging.add(viewModel.repo.getFilmPageId(it))
                    Log.d("mutableListFavFilmGet", "${viewModel.repo.getFilmPageId(it)}")
                }

                val pagingData: PagingData<FilmPage> =
                    PagingData.from(mutableListFavouriteFilmPaging)
                Log.d("mutableListFavFilmGet", "${pagingData}")
                pageAdapterFavouriteFilm.submitData(lifecycle, pagingData)
            }

        }
    }

    private fun onItemClickCollectFilm(item: String) {
        lifecycleScope.launch {
            val fragment = ProfileFragment()
            val bundle = Bundle()
            bundle.putString(ID_FILMS_COLLECT_LIST, item)
            fragment.arguments = bundle
            findNavController().navigate(
                R.id.action_profileFragment_to_collectFragment,
                bundle
            )
            Log.d("bundleFilmPage, item", "$bundle, $item")
        }
    }

    private fun onItemClickFavouriteFilm(item: Int) {
        lifecycleScope.launch {
            val fragment = ProfileFragment()
            val bundle = Bundle()
            bundle.putInt(ID_FILMS_FAVOURITE_LIST, item)
            fragment.arguments = bundle
            findNavController().navigate(
                R.id.action_profileFragment_to_filmPageFragment,
                bundle
            )
            Log.d("bundleFilmPage, item", "$bundle, $item")
        }
    }


    private fun onItemClickSeeFilm(item: Int) {
        lifecycleScope.launch {
            val fragment = ProfileFragment()
            val bundle = Bundle()
            bundle.putInt(ID_FILMS_SEE_LIST, item)
            fragment.arguments = bundle
            findNavController().navigate(
                R.id.action_profileFragment_to_filmPageFragment,
                bundle
            )
            Log.d("bundleFilmPage, item", "$bundle, $item")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            try {
                binding.apply {
                    val concatAdapter =
                        ConcatAdapter(pageAdapterSeeFilm, pageAdapterCollectFilmFooter)
                    val concatAdapterFavourite =
                        ConcatAdapter(pageAdapterFavouriteFilm, pageAdapterFavouriteFilmFooter)
                    recyclerSeeFilms.adapter = concatAdapter
                    recyclerInterestingFilms.adapter = concatAdapterFavourite
                    recyclerCollectFilm.adapter = pageAdapterCollectFilm
                    textSeeFilmsSize.text = "${mutableListSeeFilmPaging.size}"
                    textInterestingSize.text = "${mutableListFavouriteFilmPaging.size}"
                    listSeeFilm()
                    listFavouriteFilm()
                    listCollectFilm()
                    collectFilmLayout.setOnClickListener {
                        addCollect()
                    }
                    home.setOnClickListener {
                        findNavController().navigate(R.id.action_profileFragment_to_homepageFragment)
                    }
                    search.setOnClickListener {
                        findNavController().navigate(R.id.action_profileFragment_to_searchFragment)
                    }
                }
            } catch (e: Exception) {
                FirebaseCrashlytics.getInstance().recordException(e)
                sheetBottomError()
            }
        }
    }

    private fun listCollectFilm() {
        viewLifecycleOwner.lifecycleScope.launch {
            if (mutableListCollectFilm.contains("Любимое")) {
                viewModel.allCollectFilm.collect { list ->
                    Log.d("mutableListCollectFilm", "$mutableListCollectFilm")
                    val pagingData: PagingData<FilmsWithCollection> = PagingData.from(list)
                    pageAdapterCollectFilm.submitData(lifecycle, pagingData)
                }
            } else {
                mutableListCollectFilm = listCollect
                Log.d("mutableListCollectFilm1", "$mutableListCollectFilm")
                mutableListCollectFilm.forEach {
                    val collectFilm = CollectFilm(collectFilm = it)
                    if (!viewModel.existsInDatabase(collectFilm)) {
                        viewModel.addMovieToCollection(collectFilm)
                    } else {
                        Log.d("DBCollect", "Фильм $it уже существует в базе данных.")
                    }
                }

                viewModel.allCollectFilm.collect { list ->
                    Log.d("mutableListCollectFilm1", "$list")
                    val pagingData: PagingData<FilmsWithCollection> = PagingData.from(list)
                    pageAdapterCollectFilm.submitData(lifecycle, pagingData)
                }
            }
        }
    }

    private fun listSeeFilm() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.allUserMovie.collect { list ->
                list.forEach {
                    if (it.seeFilm) {
                        mutableListSeeFilm.add(it.movieId)
                        Log.d("mutableListSeeFilm", "$mutableListSeeFilm")
                    }
                }
                mutableListSeeFilm.forEach {
                    mutableListSeeFilmPaging.add(viewModel.repo.getFilmPageId(it))
                    Log.d("mutableListSeeFilmGet", "${viewModel.repo.getFilmPageId(it)}")
                }
                if (mutableListSeeFilmPaging.isEmpty()) {
                    binding.recyclerSeeFilms.visibility = View.GONE
                    binding.textNoSeeFilm.visibility = View.VISIBLE
                } else {
                    binding.recyclerSeeFilms.visibility = View.VISIBLE
                    binding.textNoSeeFilm.visibility = View.GONE
                }
                val pagingData: PagingData<FilmPage> =
                    PagingData.from(mutableListSeeFilmPaging)
                Log.d("mutableListSeeFilmGet", "${pagingData}")
                pageAdapterSeeFilm.submitData(lifecycle, pagingData)
            }
        }
    }

    private fun listFavouriteFilm() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.allUserMovie.collect { list ->
                list.forEach {
                    if (it.favoritesFilm) {
                        mutableListFavouriteFilm.add(it.movieId)
                        Log.d("mutableListFavFilm", "$mutableListFavouriteFilm")
                    }
                }
                mutableListFavouriteFilm.forEach {
                    mutableListFavouriteFilmPaging.add(viewModel.repo.getFilmPageId(it))
                    Log.d("mutableListFavFilmGet", "${viewModel.repo.getFilmPageId(it)}")
                }
                if (mutableListFavouriteFilmPaging.isEmpty()) {
                    binding.recyclerInterestingFilms.visibility = View.GONE
                    binding.textNoInterestingFilm.visibility = View.VISIBLE
                } else {
                    binding.recyclerInterestingFilms.visibility = View.VISIBLE
                    binding.textNoInterestingFilm.visibility = View.GONE
                }
                val pagingData: PagingData<FilmPage> =
                    PagingData.from(mutableListFavouriteFilmPaging)
                Log.d("mutableListSeeFilmGet", "${pagingData}")
                pageAdapterFavouriteFilm.submitData(lifecycle, pagingData)
            }
        }
    }

    private fun addCollect() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                val bottomSheetDialogCollAdd = BottomSheetDialog(requireContext())
                val view = LayoutInflater.from(context).inflate(R.layout.name_collect, null)
                bottomSheetDialogCollAdd.setContentView(view)
                bottomSheetDialogCollAdd.show()
                val close: ImageView = view.findViewById(R.id.closeNameCollect)
                close.setOnClickListener {
                    bottomSheetDialogCollAdd.dismiss()
                    Log.d("close", "close")
                }
                val addCollect: Button = view.findViewById(R.id.button)
                addCollect.setOnClickListener {
                    lifecycleScope.launch {
                        Log.d("close", "close")
                        val editText: EditText = view.findViewById(R.id.edit_text)
                        val nameCollect = editText.text.toString()
                        val pattern = Regex("^[А-Я].*")
                        val nameCollectTextCaps = pattern.matches(nameCollect)
                        viewModel.allCollectFilm.collect { collect ->
                            Log.d("recCollectList", "$collect")
                            val map = collect.map { it.collectFilm.collectFilm }
                            Log.d("recCollectMap", "$map")
                            if (!map.contains(nameCollect) && nameCollect.isNotEmpty() && nameCollectTextCaps) {
                                val collectFilm = CollectFilm(collectFilm = nameCollect)
                                viewModel.addMovieToCollection(collectFilm)
                                val pagingData: PagingData<FilmsWithCollection> =
                                    PagingData.from(collect)
                                pageAdapterCollectFilm.submitData(lifecycle, pagingData)
                                bottomSheetDialogCollAdd.dismiss()
                                return@collect cancel()
                            } else {
                                Toast.makeText(
                                    requireContext(),
                                    "Введите другое имя коллекции, коллекция с таким именем существует или " +
                                            "значение пустое! Ввод текста осуществляется с большой буквы!",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                            return@collect cancel()
                        }
                    }
                }
            }
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
        const val ID_FILMS_SEE_LIST = "id film see list"
        const val ID_FILMS_FAVOURITE_LIST = "id film favourite list"
        const val ID_FILMS_COLLECT_LIST = "id collect list"
    }
}
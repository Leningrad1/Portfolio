package com.example.cinemapark.presentation

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cinemapark.R
import com.example.cinemapark.databinding.FragmentFilmPageBinding
import com.example.cinemapark.presentation.SeasonsFragment.Companion.BACK_ID
import com.example.cinemapark.dataclass.dbcollectfilm.CollectFilm
import com.example.cinemapark.dataclass.dbcollectfilm.Movie
import com.example.cinemapark.dataclass.dbcollectfilm.MovieListMovie
import com.example.cinemapark.dataclass.filmpage.FilmPage
import com.example.cinemapark.dataclass.role.RoleItem
import com.example.cinemapark.dataclass.similar.Item
import com.example.cinemapark.presentation.ActorPageFragment.Companion.ACTOR_PAGE_FILM
import com.example.cinemapark.presentation.ActorPageFragment.Companion.FILM_ID_BEST
import com.example.cinemapark.presentation.BestFilmFragment.Companion.FILM_PAGE
import com.example.cinemapark.presentation.HomepageFragment.Companion.ID_FILMS
import com.example.cinemapark.presentation.ListPageFragment.Companion.ID_FILMS_LIST_PAGE
import com.example.cinemapark.presentation.SearchFragment.Companion.ITEM_ID
import com.example.cinemapark.presentation.recyclerviewadapter.collect.FilmCollectListAdapters
import com.example.cinemapark.presentation.recyclerviewadapter.collect.OnCheckBoxClickListener
import com.example.cinemapark.presentation.recyclerviewadapter.gallery.FilmPagingGalleryListAdapter
import com.example.cinemapark.presentation.recyclerviewadapter.role.FilmPagingRoleListAdapters
import com.example.cinemapark.presentation.recyclerviewadapter.similar.FilmPagingSimilarListAdapters
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.crashlytics.FirebaseCrashlytics
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


class FilmPageFragment : Fragment() {

    private var _binding: FragmentFilmPageBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels()
    private var id = 0
    private val pageAdapterRole = FilmPagingRoleListAdapters(::onItemClickRole)
    private val pageAdapterWorkFilms = FilmPagingRoleListAdapters(::onItemClickRole)
    private val pageAdapterGallery = FilmPagingGalleryListAdapter(::onItemClickGallery)
    private val pageAdapterSimilar = FilmPagingSimilarListAdapters(::onItemClickSimilar)
    private var idFilmGal: Int = 0
    private var idFilmPageActor = 0
    private var seeFilm = false
    private var favoriteFilm = false
    private var likeFilm = false
    private val collectFilmForAdapter =
        mutableListOf("Русское кино", "Хочу посмотреть", "Любимое")
    private var booleanCheckBox: Boolean = false
    private var pageAdapterCollect =
        FilmCollectListAdapters(::onItemClickCollect, id)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilmPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = this.arguments
        binding.apply {
            recyclerRole.adapter = pageAdapterRole
            recyclerWorkFilm.adapter = pageAdapterWorkFilms
            recyclerGallery.adapter = pageAdapterGallery
            recyclerSimilar.adapter = pageAdapterSimilar
        }
        lifecycleScope.launch {
            try {
                var idBundle = bundle!!.getInt(ID_FILMS)
                id = idBundle
                val idActorPage =
                    bundle.getInt(ACTOR_PAGE_FILM) //При возвращении со страницы информации об актере, т.к. оттуда передается id актера/режиссера
                val idBestFilm = bundle.getInt(FILM_PAGE)
                val idActorPageItem = bundle.getInt(FILM_ID_BEST)
                val idFilmListPageFragment = bundle.getInt(ID_FILMS_LIST_PAGE)
                val idSeasonsFragment = bundle.getInt(BACK_ID)
                val idSearchFragment = bundle.getInt(ITEM_ID)
                val idFilmPage = bundle.getInt(FILM_PAGE_FRAGMENT)
                val idFilmProfile = bundle.getInt(ProfileFragment.ID_FILMS_SEE_LIST)
                val idFilmProfileFavourite = bundle.getInt(ProfileFragment.ID_FILMS_FAVOURITE_LIST)
                val idCollectFragment = bundle.getInt(CollectFragment.ID_COLLECT)
                Log.d("idActorPage", "$idActorPage")
                Log.d("id", "$id")
                idFilmGal = id
                idFilmPageActor = id
                if (id == 0) {
                    id = idActorPage
                }
                if (id == 0) {
                    id = idBestFilm
                }
                if (id == 0) {
                    id = idActorPageItem
                }
                if (id == 0) {
                    id = idFilmListPageFragment
                }
                if (id == 0) {
                    id = idSeasonsFragment
                }
                if (id == 0) {
                    id = idSearchFragment
                }
                if (id == 0) {
                    id = idFilmPage
                }
                if (id == 0) {
                    id = idFilmProfile
                }
                if (id == 0) {
                    id = idFilmProfileFavourite
                }
                if (id == 0) {
                    id = idCollectFragment
                }
                viewModel.setValue(id)
                pageAdapterCollect =
                    FilmCollectListAdapters(::onItemClickCollect, id)
                Log.d("reporesglideId", "$id")
                delay(1000)
                viewModel.reloadTest(id)
                delay(1000)
                val name = viewModel.state.value
                Log.d("reponame", "$name")
                val roleInfo = viewModel.repo.getRoleInfo(id)
                val roleInfoDirector = mutableListOf<RoleItem>()
                val roleInfoActor = mutableListOf<RoleItem>()
                val similarInfo = viewModel.repo.getSimilarId(id)
                roleInfo.forEach {
                    if (it.professionKey == "DIRECTOR") {
                        roleInfoDirector.add(it)
                    } else {
                        roleInfoActor.add(it)
                    }
                }
                if (roleInfoActor.size > 19) {
                    val pagingData: PagingData<RoleItem> =
                        PagingData.from(roleInfoActor.subList(0, 20))
                    pageAdapterRole.submitData(lifecycle, pagingData)
                } else {
                    val pagingData: PagingData<RoleItem> =
                        PagingData.from(roleInfoActor.subList(0, roleInfoActor.size))
                    pageAdapterRole.submitData(lifecycle, pagingData)
                    binding.apply {
                        quantity.visibility = View.GONE
                        forward.visibility = View.GONE
                    }
                }
                if (roleInfoDirector.size > 6) {
                    val pagingData: PagingData<RoleItem> =
                        PagingData.from(roleInfoDirector.subList(0, 6))
                    pageAdapterWorkFilms.submitData(lifecycle, pagingData)
                } else {
                    val pagingData: PagingData<RoleItem> =
                        PagingData.from(roleInfoDirector.subList(0, roleInfoDirector.size))
                    pageAdapterWorkFilms.submitData(lifecycle, pagingData)
                    binding.apply {
                        quantityWorkFilm.visibility = View.GONE
                        forwardWorkFilm.visibility = View.GONE
                    }
                }
                if (similarInfo.items.size > 19) {
                    val pagingData: PagingData<Item> =
                        PagingData.from(similarInfo.items.subList(0, 6))
                    pageAdapterSimilar.submitData(lifecycle, pagingData)
                } else {
                    val pagingData: PagingData<Item> =
                        PagingData.from(similarInfo.items.subList(0, similarInfo.items.size))
                    pageAdapterSimilar.submitData(lifecycle, pagingData)
                    binding.apply {
                        quantitySimilar.visibility = View.GONE
                        forwardSimilar.visibility = View.GONE
                    }
                }
                binding.apply {
                    Glide
                        .with(view)
                        .load(name?.logoUrl)
                        .into(nameFilmPoster)
                    Glide
                        .with(view)
                        .load(name?.posterUrl)
                        .into(posterFilm)
                    Log.d("repoGlidePoster", "${name?.posterUrl}")
                    Log.d("repoGlideLogo", "${name?.logoUrl}")
                    rangText.text = name?.ratingKinopoisk.toString()
                    nameFilm.text = name?.nameRu
                    Log.d("repoRangText", name?.ratingKinopoisk.toString())
                    descript.text = name?.description
                    yearFilm.text = name?.year.toString() + " год"
                    genreFilm.text = name?.genres?.get(0)?.genre.toString()
                    countryFilm.text = name?.countries?.get(0)?.country
                    timeFilm.text = name?.filmLength.toString() + " мин"
                    ageFilm.text = name?.ratingAgeLimits
                    quantity.text = roleInfoActor.size.toString()
                    quantityWorkFilm.text = roleInfoDirector.size.toString()
                    if (viewModel.repo.getSeasonsId(id).items.isNotEmpty()) {
                        linearLayoutSeasons.visibility = View.VISIBLE
                        numberSerias.text =
                            "${viewModel.repo.getSeasonsId(id).total} сезон. ${
                                viewModel.repo.getSeasonsId(id).items[0].episodes.size
                            } серий"

                        allSerias.setOnClickListener {
                            val fragment = FilmPageFragment()
                            val bundle = Bundle()
                            bundle.putInt(ID_SEASONS, id)
                            bundle.putString(NAME_FILM, name?.nameRu)
                            fragment.arguments = bundle
                            findNavController().navigate(
                                R.id.action_filmPageFragment_to_seasonsFragment,
                                bundle
                            )
                        }
                    }
                    if (roleInfoDirector.size < 2) {
                        recyclerWorkFilm.layoutManager = GridLayoutManager(requireContext(), 1)
                    }
                    val totalPageGallery = viewModel.repo.getGalleryId(id, 1).totalPages
                    quantityGallery.text =
                        viewModel.repo.getGalleryId(id, totalPageGallery).total.toString()
                    liftBack.setOnClickListener {
                        findNavController().navigate(R.id.action_filmPageFragment_to_homepageFragment)
                    }
                    likeFilmPreview(id)
                    favouriteFilmPreview(id)
                    seeFilmPreview(id)
                    iconShare.setOnClickListener {
                        share()
                    }
                    iconOther.setOnClickListener {
                        sheetBottom(name, id)
                        FirebaseCrashlytics.getInstance().log("This sheet bottom")
                    }
                    iconLike.setOnClickListener {
                        likeFilm(id)
                    }
                    iconFavorites.setOnClickListener {
                        favoriteFilm(id)
                    }
                    iconNotLook.setOnClickListener {
                        seeFilm(id)
                    }
                    home.setOnClickListener {
                        findNavController().navigate(R.id.action_filmPageFragment_to_homepageFragment)
                    }
                    search.setOnClickListener {
                        findNavController().navigate(R.id.action_filmPageFragment_to_searchFragment)
                    }
                    profile.setOnClickListener {
                        findNavController().navigate(R.id.action_filmPageFragment_to_profileFragment)
                    }
                    Log.d("repoDescript", "${name?.description}")
                }
                onItemClickCollect(id)
                viewModel.pageGallery.onEach {
                    pageAdapterGallery.submitData(it)
                }.launchIn(viewLifecycleOwner.lifecycleScope)
                onClickForward(id)
            } catch (e: Exception) {
                sheetBottomError()
                FirebaseCrashlytics.getInstance().recordException(e)
            }
        }
    }

    private fun likeFilm(id: Int) {
        lifecycleScope.launch {
            try {
                viewModel.allUserMovie.collect { list ->
                    when {
                        list.isEmpty() -> {
                            binding.iconLike.setImageResource(R.drawable.icon_like)
                            viewModel.onAddFilmLikeFavSee(
                                movieId = id,
                                filmLike = true,
                                filmFavourite = favoriteFilm,
                                filmSee = seeFilm
                            )
                            likeFilm = true
                        }

                        !list.any { it.movieId == id } -> {
                            binding.iconLike.setImageResource(R.drawable.icon_like)
                            viewModel.onAddFilmLikeFavSee(
                                movieId = id,
                                filmLike = true,
                                filmFavourite = favoriteFilm,
                                filmSee = seeFilm
                            )
                            likeFilm = true
                        }

                        else -> {
                            val existingEntry = list.firstOrNull { it.movieId == id }
                            if (existingEntry != null) {
                                if (!existingEntry.likeFilm) {
                                    binding.iconLike.setImageResource(R.drawable.icon_like)
                                    viewModel.update(
                                        userId = 1,
                                        movieId = id,
                                        likeFilm = true,
                                        favoritesFilm = favoriteFilm,
                                        seeFilm = seeFilm
                                    )
                                    likeFilm = true
                                } else {
                                    binding.iconLike.setImageResource(R.drawable.like_no)
                                    viewModel.update(
                                        userId = 1,
                                        movieId = id,
                                        likeFilm = false,
                                        favoritesFilm = favoriteFilm,
                                        seeFilm = seeFilm
                                    )
                                    likeFilm = false
                                }
                            }
                        }
                    }
                    return@collect cancel()
                }
            } catch (e: Exception) {
                viewLifecycleOwner.lifecycleScope.launch {
                    FirebaseCrashlytics.getInstance().recordException(e)
                    Log.d("errorLikeFilm", "$e")
                }
            }
        }
    }


    private fun likeFilmPreview(id: Int) {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                try {
                    viewModel.allUserMovie.collect { list ->
                        Log.d("like012", "$list")
                        val listMap = list.filter { it.movieId == id }
                        listMap.forEach {
                            likeFilm = if (it.likeFilm) {
                                binding.iconLike.setImageResource(R.drawable.icon_like)
                                Log.d("likeFilm", "likeFilm $seeFilm")
                                true
                            } else {
                                binding.iconLike.setImageResource(R.drawable.like_no)
                                Log.d("likeFilm1", "likeFilm $seeFilm")
                                false
                            }
                        }

                    }
                } catch (e: Exception) {
                    viewLifecycleOwner.lifecycleScope.launch {
                        FirebaseCrashlytics.getInstance().recordException(e)
                        Log.d("errorLikeFilmPreview", "$e")
                    }
                }
            }
        }
    }

    private fun favoriteFilm(id: Int) {
        lifecycleScope.launch {
            try {
                viewModel.allUserMovie.collect { list ->
                    when {
                        list.isEmpty() -> {
                            binding.iconFavorites.setImageResource(R.drawable.favourite_yes)
                            viewModel.onAddFilmLikeFavSee(
                                movieId = id,
                                filmLike = likeFilm,
                                filmFavourite = true,
                                filmSee = seeFilm
                            )
                            favoriteFilm = true
                        }

                        !list.any { it.movieId == id } -> {
                            binding.iconFavorites.setImageResource(R.drawable.favourite_yes)
                            viewModel.onAddFilmLikeFavSee(
                                movieId = id,
                                filmLike = likeFilm,
                                filmFavourite = true,
                                filmSee = seeFilm
                            )
                            favoriteFilm = true
                        }

                        else -> {
                            val existingEntry = list.firstOrNull { it.movieId == id }
                            if (existingEntry != null) {
                                if (!existingEntry.favoritesFilm) {
                                    binding.iconFavorites.setImageResource(R.drawable.favourite_yes)
                                    viewModel.update(
                                        userId = 1,
                                        movieId = id,
                                        likeFilm = likeFilm,
                                        favoritesFilm = true,
                                        seeFilm = seeFilm
                                    )
                                    favoriteFilm = true
                                } else {
                                    binding.iconFavorites.setImageResource(R.drawable.favorites_no)
                                    viewModel.update(
                                        userId = 1,
                                        movieId = id,
                                        likeFilm = likeFilm,
                                        favoritesFilm = false,
                                        seeFilm = seeFilm
                                    )
                                    favoriteFilm = false
                                }
                            }
                        }
                    }
                    return@collect cancel()
                }
            } catch (e: Exception) {
                FirebaseCrashlytics.getInstance().recordException(e)
                viewLifecycleOwner.lifecycleScope.launch {
                    Log.d("errorFavFilm", "$e")
                }
            }
        }
    }

    private fun favouriteFilmPreview(id: Int) {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                try {
                    viewModel.allUserMovie.collect { list ->
                        val listMap = list.filter { it.movieId == id }
                        Log.d("fav012", "$list")
                        listMap.forEach {
                            favoriteFilm =
                                if (it.favoritesFilm) {
                                    binding.iconFavorites.setImageResource(R.drawable.favourite_yes)
                                    Log.d("favFilm", "favFilm $seeFilm")
                                    true
                                } else {
                                    binding.iconFavorites.setImageResource(R.drawable.favorites_no)
                                    Log.d("favFilm1", "favFilm $seeFilm")
                                    false
                                }
                        }
                    }
                } catch (e: Exception) {
                    viewLifecycleOwner.lifecycleScope.launch {
                        FirebaseCrashlytics.getInstance().recordException(e)
                        Log.d("errorFavFilmPreview", "$e")
                    }
                }
            }
        }
    }

    private fun seeFilm(id: Int) {
        lifecycleScope.launch {
            try {
                viewModel.allUserMovie.collect { list ->
                    when {
                        list.isEmpty() -> {
                            binding.iconNotLook.setImageResource(R.drawable.icon_see_yes)
                            viewModel.onAddFilmLikeFavSee(
                                movieId = id,
                                filmLike = likeFilm,
                                filmFavourite = favoriteFilm,
                                filmSee = true
                            )
                            seeFilm = true
                        }

                        !list.any { it.movieId == id } -> {
                            binding.iconNotLook.setImageResource(R.drawable.icon_see_yes)
                            viewModel.onAddFilmLikeFavSee(
                                movieId = id,
                                filmLike = likeFilm,
                                filmFavourite = favoriteFilm,
                                filmSee = true
                            )
                            seeFilm = true
                        }

                        else -> {
                            val existingEntry = list.firstOrNull { it.movieId == id }
                            if (existingEntry != null) {
                                if (!existingEntry.seeFilm) {
                                    binding.iconNotLook.setImageResource(R.drawable.icon_see_yes)
                                    viewModel.update(
                                        userId = 1,
                                        movieId = id,
                                        likeFilm = likeFilm,
                                        favoritesFilm = favoriteFilm,
                                        seeFilm = true
                                    )
                                    seeFilm = true
                                } else {
                                    binding.iconNotLook.setImageResource(R.drawable.not_look)
                                    viewModel.update(
                                        userId = 1,
                                        movieId = id,
                                        likeFilm = likeFilm,
                                        favoritesFilm = favoriteFilm,
                                        seeFilm = false
                                    )
                                    Log.d("seeFilm", "seeFalse")
                                    seeFilm = false
                                }
                            }
                        }
                    }
                    return@collect cancel()
                }
            } catch (e: Exception) {
                viewLifecycleOwner.lifecycleScope.launch {
                    FirebaseCrashlytics.getInstance().recordException(e)
                    Log.d("errorSeeFilm", "$e")
                }
            }
        }
    }

    private fun seeFilmPreview(id: Int) {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                try {
                    viewModel.allUserMovie.collect { list ->
                        val listMap = list.filter { it.movieId == id }
                        Log.d("see012", "$list")
                        listMap.forEach {
                            seeFilm = if (it.seeFilm) {
                                binding.iconNotLook.setImageResource(R.drawable.icon_see_yes)
                                Log.d("seeFilm1", "seeFilm $seeFilm")
                                true
                            } else {
                                binding.iconNotLook.setImageResource(R.drawable.not_look)
                                Log.d("seeFilm2", "seeFilm $seeFilm")
                                false
                            }
                        }
                    }
                } catch (e: Exception) {
                    sheetBottomError()
                    viewLifecycleOwner.lifecycleScope.launch {
                        FirebaseCrashlytics.getInstance().recordException(e)
                        Log.d("errorSeeFilmPreview", "$e")
                    }
                }
            }
        }
    }

    private fun share() {
        lifecycleScope.launch {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(
                Intent.EXTRA_TEXT,
                "Привет, посмотри обязательно этот фильм ${viewModel.state.value?.webUrl}"
            )
            startActivity(Intent.createChooser(shareIntent, "Поделиться через:"))
        }
    }

    @SuppressLint("SetTextI18n", "InflateParams", "MissingInflatedId")
    private fun sheetBottom(name: FilmPage?, id: Int) {
        try {
            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    val bottomSheetDialog = BottomSheetDialog(requireContext())
                    val view = LayoutInflater.from(context).inflate(R.layout.item_botom_sheet, null)
                    bottomSheetDialog.setContentView(view)
                    val recyclerCollect: RecyclerView = view.findViewById(R.id.recyclerCollect)
                    recyclerCollect.adapter = pageAdapterCollect
                    val postCard: ImageView = view.findViewById(R.id.postIS)
                    Glide
                        .with(postCard)
                        .load(name?.posterUrl)
                        .into(postCard)
                    collectFilmForAdapter.forEach { collect ->
                        Log.d("recCollectList", collect)
                        val collectFilm = CollectFilm(collectFilm = collect)
                        if (!viewModel.existsInDatabase(collectFilm)) {
                            viewModel.addMovieToCollection(collectFilm)
                            Log.d("collect2", "${viewModel.allCollectFilm.value} ")
                        }
                    }
                    Log.d("collect21", "${viewModel.allCollectFilm.value} ")
                    val textWantSee: TextView = view.findViewById(R.id.moveGenreIS)
                    textWantSee.text =
                        "${name?.year}, ${name?.genres?.firstOrNull()?.genre.toString()}"
                    val textFilmName: TextView = view.findViewById(R.id.moveTitleIS)
                    textFilmName.text = name?.nameRu
                    val textRang: TextView = view.findViewById(R.id.textRangIS)
                    textRang.text = name?.ratingKinopoisk.toString()
                    val plusImg: ImageView = view.findViewById(R.id.plusIS)
                    plusImg.setOnClickListener {
                        lifecycleScope.launch {
                            addCollect(id)
                        }
                    }
                    val close: ImageView = view.findViewById(R.id.closeIS)
                    close.setOnClickListener {
                        Log.d("close", "close")
                        bottomSheetDialog.dismiss()
                    }

                    bottomSheetDialog.show()
                    Log.d("allCollectFilm", "${viewModel.allCollectFilm.value}")
                    viewModel.allCollectFilm.collect { mut ->
                        mut.forEach {
                            Log.d("mutCollect", "$it, ${!viewModel.existsInDatabase(it.collectFilm)}")
                            if (!collectFilmForAdapter.contains(it.collectFilm.collectFilm)) {
                                collectFilmForAdapter.add(it.collectFilm.collectFilm)
                                Log.d("mutCheck", "$collectFilmForAdapter $it")
                            }
                            Log.d("mutCollect", "$mut")
                            val pagingData = PagingData.from(mut)
                            booleanCheckBox = viewModel.daoRepository.isMovieInCollection(
                                id,
                                it.collectFilm.collectionId
                            )
                            Log.d("mutCollect1", "$booleanCheckBox")
                            pageAdapterCollect.submitData(lifecycle, pagingData)
                        }
                    }
                }
            }
        } catch (e: Exception) {
            FirebaseCrashlytics.getInstance().recordException(e)
            sheetBottomError()
            Log.d("sheetBottomErrorSheetBottom", "$e")
        }
    }

    private fun addCollect(id: Int) {
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
                                viewModel.addMovieToCollection(
                                    collectFilm = CollectFilm(collectFilm = nameCollect)
                                )
                                collectFilmForAdapter.add(nameCollect)
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

    private fun onClickForward(item: Int) {
        lifecycleScope.launch {
            binding.apply {
                forward.setOnClickListener {
                    val fragment = FilmPageFragment()
                    val bundle = Bundle()
                    bundle.putInt(ID_FILMS_ROLE, item)
                    fragment.arguments = bundle
                    findNavController().navigate(
                        R.id.action_filmPageFragment_to_filmActorAllFragment,
                        bundle
                    )
                }
                forwardWorkFilm.setOnClickListener {
                    val fragment = FilmPageFragment()
                    val bundle = Bundle()
                    bundle.putInt(ID_FILMS_ROLE, item)
                    fragment.arguments = bundle
                    findNavController().navigate(
                        R.id.action_filmPageFragment_to_filmActorAllFragment,
                        bundle
                    )
                }
                forwardGallery.setOnClickListener {
                    val fragment = FilmPageFragment()
                    val bundle = Bundle()
                    bundle.putInt(ID_FILMS_ROLE, item)
                    fragment.arguments = bundle
                    findNavController().navigate(
                        R.id.action_filmPageFragment_to_galleryFragment,
                        bundle
                    )
                }
            }
        }
    }

    private fun onItemClickRole(item: Int) {
        lifecycleScope.launch {
            val fragment = FilmPageFragment()
            val bundle = Bundle()
            bundle.putInt(ACTOR_PAGE, item)
            bundle.putInt(ID_FILM, idFilmPageActor)
            fragment.arguments = bundle
            findNavController().navigate(
                R.id.action_filmPageFragment_to_actorPageFragment,
                bundle
            )
            Log.d("bundleFilmPage", "$bundle")
        }
    }

    private fun onItemClickSimilar(item: Int) {
        lifecycleScope.launch {
            val fragment = FilmPageFragment()
            val bundle = Bundle()
            bundle.putInt(FILM_PAGE_FRAGMENT, item)
            fragment.arguments = bundle
            findNavController().navigate(
                R.id.filmPageFragment,
                bundle
            )
            Log.d("bundleFilmPage", "$bundle")
        }
    }

    private fun onItemClickGallery(item: String) {
        val fragment = FilmPageFragment()
        val bundle = Bundle()
        bundle.putInt(URL_GALLERY, idFilmGal)
        bundle.putString(OPEN_URL_GALLERY, item)
        fragment.arguments = bundle
        findNavController().navigate(
            R.id.action_filmPageFragment_to_imageSlideFragment,
            bundle
        )
    }

    private fun onItemClickCollect(item: Int?) {
        Log.d("check", "check")
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                Log.d("check1", "check1")
                setOnClickListener()
            }
        }
    }

    private fun setOnClickListener() {
        viewLifecycleOwner.lifecycleScope.launch {
            pageAdapterCollect.setOnCheckBoxClickListener(object : OnCheckBoxClickListener {
                override fun onCheckBoxClicked(position: Int, isCheckBoxChecked: Boolean) {
                    lifecycleScope.launch {
                        val allCollectFilm = viewModel.allCollectFilm.value
                        var collectCheck = collectFilmForAdapter[position]
                        Log.d("check", "$collectCheck $position")
                        Log.d("check2", "check2")
                        val matchedCollections = allCollectFilm.filter {
                            it.collectFilm.collectFilm == collectCheck &&
                                    it.collectFilm.userId == 1
                        }
                        matchedCollections.forEach { collection ->
                            Log.d("check3", "$collection")
                            collection.movieList.forEach {
                                Log.d("check4", "$it")
                                it.isCheck = isCheckBoxChecked
                            }
                            val movie =
                                collection.collectFilm.let {
                                    Movie(
                                        movieId = id,
                                        collectionId = it.collectionId,
                                        isCheck = isCheckBoxChecked
                                    )
                                }
                            Log.d("check5", "$movie")

                            if (isCheckBoxChecked) {
                                Log.d("check4", "$isCheckBoxChecked")
                                Log.d("check4/1", "${collection.collectFilm.collectionId - 1}")
                                Log.d("check4/1", "$position")
                                if (!collection.movieList.contains(
                                        Movie(
                                            movieId = id,
                                            collectionId = collection.collectFilm.collectionId
                                        )
                                    )
                                    && (collection.collectFilm.collectionId - 1 == position)
                                ) {
                                    Log.d(
                                        "check6", "${
                                            !collection.movieList.contains(
                                                Movie(
                                                    movieId = id,
                                                    collectionId = collection.collectFilm.collectionId
                                                )
                                            )
                                        }"
                                    )
                                    viewModel.onAddFilm(movie)
                                    val collectAndMovieId = MovieListMovie(
                                        collection.collectFilm.collectionId,
                                        movie.movieId
                                    )
                                    viewModel.onAddMovieList(collectAndMovieId)
                                    Log.d(
                                        "addFilmCollectionCheck",
                                        "${movie}, $allCollectFilm"
                                    )
                                } else {
                                    Log.d(
                                        "check7", "${
                                            !collection.movieList.contains(
                                                Movie(
                                                    movieId = id,
                                                    collectionId = collection.collectFilm.collectionId
                                                )
                                            )
                                        }"
                                    )
                                    viewModel.onUpdateFilm(movie)
                                    Log.d(
                                        "updateFilmCollectionCheck",
                                        "${movie}, $allCollectFilm"
                                    )
                                }
                            } else {
                                val collectAndMovieId = MovieListMovie(
                                    collection.collectFilm.collectionId,
                                    movie.movieId
                                )
                                viewModel.deleteMovieToCollection(collectAndMovieId)
                                Log.d(
                                    "deleteFilmCollectionCheck",
                                    "${movie}, $allCollectFilm"
                                )
                            }
                        }
                    }
                }
            })
        }
    }

    private fun sheetBottomError() {
        viewLifecycleOwner.lifecycleScope.launch {
            val bottomSheetDialog = BottomSheetDialog(requireContext())
            val view =
                LayoutInflater.from(context).inflate(R.layout.item_botom_sheet_error, null)
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
        const val ID_FILMS_ROLE = "id"
        const val FILM_PAGE_FRAGMENT = "reload id"
        const val URL_GALLERY = "url"
        const val OPEN_URL_GALLERY = "url_open"
        const val ACTOR_PAGE = "staff id"
        const val ID_FILM = "id film"
        const val ID_SEASONS = "seasons"
        const val NAME_FILM = "name film"
    }
}
package com.example.cinemapark.presentation

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.cinemapark.data.db.DaoRepository
import com.example.cinemapark.data.retrofit.FilmRepository
import com.example.cinemapark.dataclass.combined.CombinedDrameFilm
import com.example.cinemapark.dataclass.combined.CombinedPop
import com.example.cinemapark.dataclass.combined.CombinedPremier
import com.example.cinemapark.dataclass.combined.CombinedSeries
import com.example.cinemapark.dataclass.combined.CombinedTopFilm
import com.example.cinemapark.dataclass.combined.CombinedUsaMilitary
import com.example.cinemapark.dataclass.dbcollectfilm.CollectFilm
import com.example.cinemapark.dataclass.dbcollectfilm.Movie
import com.example.cinemapark.dataclass.dbcollectfilm.MovieListMovie
import com.example.cinemapark.dataclass.dbcollectfilm.NewUserMovie
import com.example.cinemapark.dataclass.eTop.Item
import com.example.cinemapark.dataclass.filmpage.FilmPage
import com.example.cinemapark.dataclass.genrecountry.Country
import com.example.cinemapark.dataclass.genrecountry.Genre
import com.example.cinemapark.dataclass.popular.ItemPopular
import com.example.cinemapark.dataclass.premier.ItemPremier
import com.example.cinemapark.dataclass.searchkeyword.Film
import com.example.cinemapark.presentation.recyclerviewadapter.action.FilmUsaActionPagingSource
import com.example.cinemapark.presentation.recyclerviewadapter.drame.FilmDrameFrancePagingSource
import com.example.cinemapark.presentation.recyclerviewadapter.gallery.FilmGalleryPagingSourse
import com.example.cinemapark.presentation.recyclerviewadapter.galleryframe.FilmGalleryFramePagingSourse
import com.example.cinemapark.presentation.recyclerviewadapter.gallerypost.FilmGalleryPostPagingSourse
import com.example.cinemapark.presentation.recyclerviewadapter.popular.FilmPopularPagingSourse
import com.example.cinemapark.presentation.recyclerviewadapter.premier.FilmPremierPagingSourse
import com.example.cinemapark.presentation.recyclerviewadapter.searchfilmparam.SearchFilmParamPagingSource
import com.example.cinemapark.presentation.recyclerviewadapter.searchkeyword.SearchKeyWordPagingSource
import com.example.cinemapark.presentation.recyclerviewadapter.serias.SeriasPagingSource
import com.example.cinemapark.presentation.recyclerviewadapter.top.FilmsTopPagingSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class MainViewModel() : ViewModel() {

    private val pagePremierFilms: Flow<PagingData<ItemPremier>> = Pager(
        config = PagingConfig(pageSize = 0),
        pagingSourceFactory = { FilmPremierPagingSourse() }
    ).flow.cachedIn(viewModelScope)

    private val pagePopularFilms: Flow<PagingData<ItemPopular>> = Pager(
        config = PagingConfig(pageSize = 10),
        pagingSourceFactory = { FilmPopularPagingSourse() }
    ).flow.cachedIn(viewModelScope)

    private val pageUsaMilFilms: Flow<PagingData<com.example.cinemapark.dataclass.action.Item>> = Pager(
        config = PagingConfig(pageSize = 10),
        pagingSourceFactory = { FilmUsaActionPagingSource() }
    ).flow.cachedIn(viewModelScope)

    private val pageTopFilms: Flow<PagingData<Item>> = Pager(
        config = PagingConfig(pageSize = 10),
        pagingSourceFactory = { FilmsTopPagingSource() }
    ).flow.cachedIn(viewModelScope)

    private val pageDrameFrFilms: Flow<PagingData<com.example.cinemapark.dataclass.drame.Item>> = Pager(
        config = PagingConfig(pageSize = 10),
        pagingSourceFactory = { FilmDrameFrancePagingSource() }
    ).flow.cachedIn(viewModelScope)

    private val pageSerias: Flow<PagingData<com.example.cinemapark.dataclass.serias.Item>> = Pager(
        config = PagingConfig(pageSize = 10),
        pagingSourceFactory = { SeriasPagingSource() }
    ).flow.cachedIn(viewModelScope)

    private val _state = MutableStateFlow<FilmPage?>(null)
    val state = _state.asStateFlow()
    val repo = FilmRepository()
    var id = 0
    var chilBoolean = -1
    var count = 1
    suspend fun reloadTest(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = repo.getFilmPageId(id)
                Log.d("repo1", "${result}")
                _state.value = result
                Log.d("repo", "${_state.value}")
            } catch (e: Throwable) {
                Log.d("repoview", "$e")
            }
        }
    }

    private val valueGalleryId = MutableLiveData<Int>()
    fun setValue(value: Int) {
        valueGalleryId.value = value
    }

    private val valueSearchKeyWord = MutableLiveData<String>()
    fun setValueSearchKeyWord(value: String) {
        valueSearchKeyWord.value = value
        Log.d("editTextset", "$value")
    }

    var filmActor = ArrayList<Int?>()
    val pageGallery: Flow<PagingData<com.example.cinemapark.dataclass.gallery.Item>> = Pager(
        config = PagingConfig(pageSize = 0),
        pagingSourceFactory = { FilmGalleryPagingSourse(valueGalleryId.value!!) }
    ).flow.cachedIn(viewModelScope)
    val pageGalleryAll: Flow<PagingData<com.example.cinemapark.dataclass.gallery.Item>> = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = { FilmGalleryPagingSourse(valueGalleryId.value!!) }
    ).flow.cachedIn(viewModelScope)
    val pageGalleryPostAll: Flow<PagingData<com.example.cinemapark.dataclass.gallerypost.ItemPost>> =
        Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { FilmGalleryPostPagingSourse(valueGalleryId.value!!) }
        ).flow.cachedIn(viewModelScope)
    val pageGalleryFrameAll: Flow<PagingData<com.example.cinemapark.dataclass.galleryframe.ItemFrame>> =
        Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { FilmGalleryFramePagingSourse(valueGalleryId.value!!) }
        ).flow.cachedIn(viewModelScope)
    val pageSearchKeyWord: Flow<PagingData<Film>> = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = { SearchKeyWordPagingSource(repo, valueSearchKeyWord.value!!) }
    ).flow
    val pageSearchParam: Flow<PagingData<com.example.cinemapark.dataclass.searchparam.Item>> =
        Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = {
                SearchFilmParamPagingSource(
                    repo,
                    country,
                    genre,
                    order,
                    type,
                    ratingFrom,
                    ratingTo,
                    yearFrom,
                    yearTo
                )
            }
        ).flow
    val daoRepository = DaoRepository()
    val allUserMovie = daoRepository.getAllFilmId()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )
    val allCollectFilm = daoRepository.getCollect()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )
    fun onAddFilmLikeFavSee(
        movieId: Int,
        filmLike: Boolean,
        filmFavourite: Boolean,
        filmSee: Boolean
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val userMovie = NewUserMovie(
                movieId = movieId,
                seeFilm = filmSee,
                favoritesFilm = filmFavourite,
                likeFilm = filmLike
            )
            daoRepository.insertFilm(userMovie)
        }
    }

    fun onAddFilm(listFilm: Movie) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("viewModel", "$listFilm")
            Log.d("viewModel", "${allCollectFilm.value}")
            daoRepository.insertListFilm(listFilm)
        }
    }

    fun onUpdateFilm(listFilm: Movie) {
        viewModelScope.launch(Dispatchers.IO) {

            Log.d("viewModel", "$listFilm")
            Log.d("viewModel", "${allCollectFilm.value}")

            daoRepository.updateFilmCollect(listFilm)
        }
    }
    fun update(
        userId: Int,
        movieId: Int,
        likeFilm: Boolean,
        favoritesFilm: Boolean,
        seeFilm: Boolean
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            allUserMovie.value.firstOrNull().let {
                val userMovie = it?.copy(
                    id = userId,
                    movieId = movieId,
                    likeFilm = likeFilm,
                    favoritesFilm = favoritesFilm,
                    seeFilm = seeFilm
                )
                userMovie?.let { it1 ->
                    daoRepository.updateFilm(
                        userId,
                        movieId,
                        likeFilm,
                        favoritesFilm,
                        seeFilm
                    )
                }
            }
        }
    }

    fun onAddMovieList(movieList: MovieListMovie) {
        viewModelScope.launch(Dispatchers.IO) {
            val movieList = movieList
            Log.d("viewModelMovieList", "$movieList")
            daoRepository.insertMovieList(movieList)
        }
    }

    fun addMovieToCollection(collectFilm: CollectFilm) {
        viewModelScope.launch {
            Log.d("viewModelCollectList", "$collectFilm")
            daoRepository.insertFilmCollect(collectFilm)
        }
    }

    fun getCombinedPremierFlow(): Flow<PagingData<CombinedPremier>> {
        val userMoviesFlow = allUserMovie
        val itemPremiersFlow = pagePremierFilms
        return itemPremiersFlow.flatMapLatest { pagingData ->
            userMoviesFlow.map { userMovies ->
                pagingData.map { itemPremier ->
                    val userMovie = userMovies.find { it.movieId == itemPremier.kinopoiskId }
                    CombinedPremier(itemPremier, userMovie?.seeFilm ?: false)
                }
            }
        }
    }

    fun getCombinedPopularFlow(): Flow<PagingData<CombinedPop>> {
        val userMoviesFlow = allUserMovie
        val itemPopularFlow = pagePopularFilms
        return itemPopularFlow.flatMapLatest { pagingData ->
            userMoviesFlow.map { userMovies ->
                pagingData.map { itemPopular ->
                    val userMovie = userMovies.find { it.movieId == itemPopular.kinopoiskId }
                    CombinedPop(itemPopular, userMovie?.seeFilm ?: false)
                }
            }
        }
    }

    fun getCombinedUsaMilitaryFlow(): Flow<PagingData<CombinedUsaMilitary>> {
        val userMoviesFlow = allUserMovie
        val itemUsaMilitaryFlow = pageUsaMilFilms
        return itemUsaMilitaryFlow.flatMapLatest { pagingData ->
            userMoviesFlow.map { userMovies ->
                pagingData.map { itemUsaMilitary ->
                    val userMovie = userMovies.find { it.movieId == itemUsaMilitary.kinopoiskId }
                    CombinedUsaMilitary(itemUsaMilitary, userMovie?.seeFilm ?: false)
                }
            }
        }
    }
    fun getCombinedTopFilmFlow(): Flow<PagingData<CombinedTopFilm>> {
        val userMoviesFlow = allUserMovie
        val itemFlow = pageTopFilms
        return itemFlow.flatMapLatest { pagingData ->
            userMoviesFlow.map { userMovies ->
                pagingData.map { item ->
                    val userMovie = userMovies.find { it.movieId == item.kinopoiskId }
                    CombinedTopFilm(item, userMovie?.seeFilm ?: false)
                }
            }
        }
    }
    fun getCombinedDrameFilmFlow(): Flow<PagingData<CombinedDrameFilm>> {
        val userMoviesFlow = allUserMovie
        val itemFlow = pageDrameFrFilms
        return itemFlow.flatMapLatest { pagingData ->
            userMoviesFlow.map { userMovies ->
                pagingData.map { item ->
                    val userMovie = userMovies.find { it.movieId == item.kinopoiskId }
                    CombinedDrameFilm(item, userMovie?.seeFilm ?: false)
                }
            }
        }
    }
    fun getCombinedSeriesFlow(): Flow<PagingData<CombinedSeries>> {
        val userMoviesFlow = allUserMovie
        val itemFlow = pageSerias
        return itemFlow.flatMapLatest { pagingData ->
            userMoviesFlow.map { userMovies ->
                pagingData.map { item ->
                    val userMovie = userMovies.find { it.movieId == item.kinopoiskId }
                    CombinedSeries(item, userMovie?.seeFilm ?: false)
                }
            }
        }
    }
    suspend fun existsInDatabase(collectFilm: CollectFilm): Boolean {
        return daoRepository.existsInDatabase(collectFilm.collectFilm)
    }


    fun deleteMovieToCollection(movie: MovieListMovie) {
        viewModelScope.launch {
            daoRepository.deleteMovieId(movie)
        }
    }

    val countryList = mutableListOf<Country>()
    val countryListAdd = mutableListOf<Country>()
    val genreList = mutableListOf<Genre>()
    val genreListAdd = mutableListOf<Genre>()
    val listYear: List<String> = (1895..2024).map { it.toString() }

    private var country = 0
    private var genre = 0
    private var order = ""
    private var type = ""
    private var ratingFrom = 0
    private var ratingTo = 0
    private var yearFrom = 0
    private var yearTo = 0

    fun addCountry(int: Int) {
        country = int
    }

    fun addGenre(int: Int) {
        genre = int
    }

    fun addOrder(str: String) {
        order = str
    }

    fun addType(str: String) {
        type = str
    }

    fun addRatingFrom(int: Int) {
        ratingFrom = int
    }

    fun addRatingTo(int: Int) {
        ratingTo = int
    }

    fun addYearFrom(int: Int) {
        yearFrom = int
    }

    fun addYearTo(int: Int) {
        yearTo = int
    }
}
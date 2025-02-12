package com.example.cinemapark.data.db

import android.util.Log
import com.example.cinemapark.Application
import com.example.cinemapark.dataclass.dbcollectfilm.CollectFilm
import com.example.cinemapark.dataclass.dbcollectfilm.FilmsWithCollection
import com.example.cinemapark.dataclass.dbcollectfilm.Movie
import com.example.cinemapark.dataclass.dbcollectfilm.MovieListMovie
import com.example.cinemapark.dataclass.dbcollectfilm.NewUserMovie
import com.example.cinemapark.dataclass.dbcollectfilm.UserMovie
import kotlinx.coroutines.flow.Flow

class DaoRepository {
    fun getAllFilmId(): Flow<List<UserMovie>> {
        return Application.INSTANCE.db.userDao().getAllFilmId()
    }

    fun getCollect(): Flow<List<FilmsWithCollection>> {
        return Application.INSTANCE.db.userDao().getAllFilmWithMovie()
    }

    suspend fun insertFilm(film: NewUserMovie) {
        return Application.INSTANCE.db.userDao().insert(film)
    }

    suspend fun insertListFilm(movie: Movie) {
        Log.d("DaoRepository", "Inserting movie: $movie")
        Application.INSTANCE.db.userDao().insertMovies(movie)
    }

    suspend fun insertFilmCollect(collectFilm: CollectFilm) {
        Log.d("DaoRepository", "Inserting collectFilm: $collectFilm")
        Application.INSTANCE.db.userDao().insertCollect(collectFilm)
    }

    suspend fun updateFilmCollect(film: Movie) {
        return Application.INSTANCE.db.userDao().updateMovies(film)
    }

    suspend fun insertMovieList(film: MovieListMovie) {
        Log.d("insertCollect", "${film}")
        return Application.INSTANCE.db.userDao().insertMovieList(film)
    }

    suspend fun updateFilm(
        userId: Int,
        movieId: Int,
        likeFilm: Boolean,
        favoritesFilm: Boolean,
        seeFilm: Boolean
    ) {
        return Application.INSTANCE.db.userDao()
            .update(userId, movieId, likeFilm, favoritesFilm, seeFilm)
    }

    suspend fun deleteMovieId(movie: MovieListMovie) {
        return Application.INSTANCE.db.userDao().deleteMovieId(movie)
    }

    suspend fun isMovieInCollection(movieId: Int, collectionId: Int): Boolean {
        return Application.INSTANCE.db.userDao().isMovieInCollection(movieId, collectionId)
    }

    suspend fun existsInDatabase(collectFilm: String): Boolean {
        return Application.INSTANCE.db.userDao().exists(collectFilm) != null
    }
}
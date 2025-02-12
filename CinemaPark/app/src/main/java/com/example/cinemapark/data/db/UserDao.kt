package com.example.cinemapark.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.cinemapark.dataclass.dbcollectfilm.CollectFilm
import com.example.cinemapark.dataclass.dbcollectfilm.FilmsWithCollection
import com.example.cinemapark.dataclass.dbcollectfilm.Movie
import com.example.cinemapark.dataclass.dbcollectfilm.MovieListMovie
import com.example.cinemapark.dataclass.dbcollectfilm.NewUserMovie
import com.example.cinemapark.dataclass.dbcollectfilm.UserMovie
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Transaction
    @Query("SELECT * FROM user_movie")
    fun getAllFilmId(): Flow<List<UserMovie>>

    @Transaction
    @Query("SELECT * FROM collect_film")
    fun getCollectFilm(): Flow<List<CollectFilm>>

    @Transaction
    @Query("SELECT * FROM movie")
    fun getMovie(): Flow<List<Movie>>

    @Transaction
    @Query("SELECT DISTINCT * FROM collect_film")
    fun getAllFilmWithMovie(): Flow<List<FilmsWithCollection>>

    @Insert(
        onConflict = OnConflictStrategy.REPLACE,
        entity = UserMovie::class
    )
    suspend fun insert(likeFilm: NewUserMovie)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCollect(collect: CollectFilm)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: Movie)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieList(movieList: MovieListMovie)

    @Query("SELECT COUNT(*) > 0 FROM MOVIE_LIST_MOVIE WHERE movie_id = :movieId AND collection_id = :collectionId")
    suspend fun isMovieInCollection(movieId: Int, collectionId: Int): Boolean

    @Query("SELECT COUNT(*) > 0 FROM user_movie WHERE see_film = :seeFilm AND movieId = :movieId")
    suspend fun isMovieInDB(seeFilm: Boolean, movieId: Int): Boolean

    @Query("SELECT * FROM collect_film WHERE collection_id=:collectionId AND user_id=:userId AND collection_film=:collectFilm")
    suspend fun getCollectFilmById(
        collectionId: Int,
        userId: Int,
        collectFilm: String
    ): CollectFilm?

    @Update
    suspend fun updateCollect(collect: CollectFilm)

    @Update
    suspend fun updateMovies(movies: Movie)

    @Query("DELETE FROM collect_film WHERE collection_film LIKE:collectFilm")
    suspend fun deleteIdFilmCheck(collectFilm: String?)

    @Query("DELETE FROM user_movie WHERE favorites_film LIKE:favoritesFilm")
    suspend fun deleteFav(favoritesFilm: Int)

    @Query(
        "UPDATE user_movie SET like_film = :likeFilm, favorites_film = :favoritesFilm, " +
                "see_film = :seeFilm WHERE id = :userId AND movieId = :movieId"
    )
    suspend fun update(
        userId: Int,
        movieId: Int,
        likeFilm: Boolean,
        favoritesFilm: Boolean,
        seeFilm: Boolean
    )

    @Delete
    suspend fun deleteMovieId(movie: MovieListMovie)

    @Query("SELECT * FROM collect_film WHERE collection_film = :collectFilm LIMIT 1")
    suspend fun exists(collectFilm: String): CollectFilm?
}
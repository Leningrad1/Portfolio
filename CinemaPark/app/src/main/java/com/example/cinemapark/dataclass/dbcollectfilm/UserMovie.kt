package com.example.cinemapark.dataclass.dbcollectfilm

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_movie")
data class UserMovie(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "movieId")
    val movieId: Int,
    @ColumnInfo(name = "like_film")
    var likeFilm: Boolean = false,
    @ColumnInfo(name = "favorites_film")
    var favoritesFilm: Boolean = false,
    @ColumnInfo(name = "see_film")
    var seeFilm: Boolean = false
)
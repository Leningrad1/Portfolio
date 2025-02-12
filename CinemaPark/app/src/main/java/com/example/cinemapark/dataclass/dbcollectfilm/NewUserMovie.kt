package com.example.cinemapark.dataclass.dbcollectfilm

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

data class NewUserMovie(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int? = null,
    @ColumnInfo(name = "movieId")
    val movieId: Int,
    @ColumnInfo(name = "like_film")
    var likeFilm: Boolean,
    @ColumnInfo(name = "favorites_film")
    var favoritesFilm: Boolean,
    @ColumnInfo(name = "see_film")
    var seeFilm: Boolean
)
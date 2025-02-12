package com.example.cinemapark.dataclass.dbcollectfilm

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "movie_list_movie",
    primaryKeys = ["collection_id", "movie_id"]
)
data class MovieListMovie(
    @ColumnInfo(name = "collection_id")
    val collectionId: Int,
    @ColumnInfo(name = "movie_id")
    val movieId: Int
)
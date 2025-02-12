package com.example.cinemapark.dataclass.dbcollectfilm

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(
    tableName = "movie"
)
data class Movie(
    @PrimaryKey
    @ColumnInfo(name = "movie_id")
    val movieId: Int,
    @ColumnInfo(name = "collection_id")
    val collectionId: Int,
    @ColumnInfo(name = "is_check_box")
    var isCheck: Boolean = false
)

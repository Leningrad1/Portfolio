package com.example.cinemapark.dataclass.dbcollectfilm

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "collect_film"
)
data class CollectFilm(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "collection_id")
    var collectionId: Int = 0,
    @ColumnInfo(name = "user_id")
    var userId: Int = 1,
    @ColumnInfo(name = "collection_film")
    var collectFilm: String
)
package com.example.cinemapark.dataclass.dbcollectfilm

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class FilmsWithCollection(
    @Embedded
    val collectFilm: CollectFilm,
    @Relation(parentColumn = "collection_id",
        entity = Movie::class,
        entityColumn = "movie_id",
        associateBy = Junction(value = MovieListMovie::class,
            parentColumn = "collection_id",
            entityColumn = "movie_id",))
    val movieList: MutableList<Movie>
)

/*
data class MovieWithCollections( // это для обратной коллекции
    @Embedded val movie: Movie,
    @Relation(
        parentColumn = "movieId",
        entityColumn = "collectionId",
        associateBy = Junction(MovieCollectionCrossRef::class)
    )
    val collections: List<Collection>
)*/

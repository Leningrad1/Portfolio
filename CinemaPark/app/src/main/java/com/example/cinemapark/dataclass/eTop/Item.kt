package com.example.cinemapark.dataclass.eTop

data class Item(
    val countries: List<CountryX>,
    val genres: List<GenreX>,
    val kinopoiskId: Int,
    val nameEn: String,
    val nameOriginal: String,
    val nameRu: String,
    val posterUrl: String,
    val posterUrlPreview: String,
    val ratingImbd: Double,
    val ratingKinopoisk: Double,
    val type: String,
    val year: String
)

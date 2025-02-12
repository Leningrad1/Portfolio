package com.example.cinemapark.dataclass.popular

data class ItemPopular(
    val countries: List<Country>,
    val genres: List<Genre>,
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
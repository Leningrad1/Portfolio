package com.example.cinemapark.dataclass.premier


data class ItemPremier(
    val countries: List<CountryPremier>,
    val duration: Int,
    val genres: List<GenrePremier>,
    val kinopoiskId: Int,
    val nameEn: String,
    val nameRu: String,
    val posterUrl: String,
    val posterUrlPreview: String,
    val premiereRu: String,
    val year: Int
)
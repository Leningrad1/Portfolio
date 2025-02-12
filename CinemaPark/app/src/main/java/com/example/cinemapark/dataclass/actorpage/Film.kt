package com.example.cinemapark.dataclass.actorpage

data class Film(
    val description: String,
    val filmId: Int,
    val general: Boolean,
    val nameEn: String,
    val nameRu: String,
    val professionKey: String,
    val rating: String
)
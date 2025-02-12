package com.example.cinemapark.dataclass.gallery

data class Gallery(
    val items: MutableList<Item>,
    val total: Int,
    val totalPages: Int
)
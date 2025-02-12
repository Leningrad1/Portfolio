package com.example.cinemapark.dataclass.searchparam

data class SearchParam(
    val items: List<Item>,
    val total: Int,
    val totalPages: Int
)
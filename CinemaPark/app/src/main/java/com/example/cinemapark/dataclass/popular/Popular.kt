package com.example.cinemapark.dataclass.popular

data class Popular(
    val items: List<ItemPopular>,
    val total: Int,
    val totalPages: Int
)
package com.example.cinemapark.dataclass.action

data class Action(
    val items: List<Item>,
    val total: Int,
    val totalPages: Int
)
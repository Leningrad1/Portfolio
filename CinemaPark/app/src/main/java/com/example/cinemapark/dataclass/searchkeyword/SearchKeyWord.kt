package com.example.cinemapark.dataclass.searchkeyword

data class SearchKeyWord(
    val films: List<Film>,
    val keyword: String,
    val pagesCount: Int,
    val searchFilmsCountResult: Int
)
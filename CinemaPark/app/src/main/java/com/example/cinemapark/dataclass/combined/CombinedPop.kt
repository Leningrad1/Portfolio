package com.example.cinemapark.dataclass.combined

import com.example.cinemapark.dataclass.popular.ItemPopular

data class CombinedPop(
    val itemPop: ItemPopular,
    val seeFilm: Boolean)

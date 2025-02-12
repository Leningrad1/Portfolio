package com.example.cinemapark.dataclass.combined

import com.example.cinemapark.dataclass.serias.Item

data class CombinedSeries(
    val itemSeries: Item,
    val seeFilm: Boolean)

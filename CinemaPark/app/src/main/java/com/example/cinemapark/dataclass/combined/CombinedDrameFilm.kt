package com.example.cinemapark.dataclass.combined

import com.example.cinemapark.dataclass.drame.Item


data class CombinedDrameFilm(
    val itemDrame: Item,
    val seeFilm: Boolean)

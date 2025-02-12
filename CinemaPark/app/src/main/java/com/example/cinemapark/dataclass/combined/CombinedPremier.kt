package com.example.cinemapark.dataclass.combined

import com.example.cinemapark.dataclass.premier.ItemPremier

data class CombinedPremier(val itemPremier: ItemPremier,
                           val seeFilm: Boolean)

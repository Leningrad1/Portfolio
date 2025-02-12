package com.example.cinemapark.dataclass.gallerypost

data class GalleryPost(
    val items: MutableList<ItemPost>,
    val total: Int,
    val totalPages: Int
)
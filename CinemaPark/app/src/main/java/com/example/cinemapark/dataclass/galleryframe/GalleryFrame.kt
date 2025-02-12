package com.example.cinemapark.dataclass.galleryframe

data class GalleryFrame(
    val items: MutableList<ItemFrame>,
    val total: Int,
    val totalPages: Int
)
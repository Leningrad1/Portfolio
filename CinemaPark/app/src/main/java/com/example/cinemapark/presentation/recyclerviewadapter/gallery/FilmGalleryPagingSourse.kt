package com.example.cinemapark.presentation.recyclerviewadapter.gallery

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.cinemapark.data.retrofit.FilmRepository
import com.example.cinemapark.dataclass.gallery.Item

class FilmGalleryPagingSourse(private var id: Int) : PagingSource<Int, Item>() {
    private val repository = FilmRepository()
    override fun getRefreshKey(state: PagingState<Int, Item>): Int = GALLERY_PAGE
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Item> {

        val pageGallery = params.key ?: 1
         return kotlin.runCatching {
             repository.getGalleryId(id, pageGallery).items
        }.fold(
            onSuccess = {
                LoadResult.Page(
                    data = it,
                    prevKey = null,
                    nextKey = if (it.isEmpty()) null else pageGallery + 1
                )
            },
            onFailure = { LoadResult.Error(it) }
        )
    }

    companion object {
        private const val GALLERY_PAGE = 1
    }
}
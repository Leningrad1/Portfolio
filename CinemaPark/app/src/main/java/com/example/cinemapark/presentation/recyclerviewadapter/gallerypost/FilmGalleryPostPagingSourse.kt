package com.example.cinemapark.presentation.recyclerviewadapter.gallerypost

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.cinemapark.data.retrofit.FilmRepository
import com.example.cinemapark.dataclass.gallerypost.ItemPost

class FilmGalleryPostPagingSourse(private var id: Int) : PagingSource<Int, ItemPost>() {
    private val repository = FilmRepository()
    override fun getRefreshKey(state: PagingState<Int, ItemPost>): Int? = GALLERYPOST_PAGE
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ItemPost> {

        val pageGallery = params.key ?: 1
         return kotlin.runCatching {
             repository.getGalleryIdPost(id, pageGallery).items
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
        private val GALLERYPOST_PAGE = 1
    }
}
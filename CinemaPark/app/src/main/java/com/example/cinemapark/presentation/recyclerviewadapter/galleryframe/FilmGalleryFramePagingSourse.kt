package com.example.cinemapark.presentation.recyclerviewadapter.galleryframe

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.cinemapark.data.retrofit.FilmRepository
import com.example.cinemapark.dataclass.galleryframe.ItemFrame

class FilmGalleryFramePagingSourse(private var id: Int) : PagingSource<Int, ItemFrame>() {
    private val repository = FilmRepository()
    override fun getRefreshKey(state: PagingState<Int, ItemFrame>): Int? = GALLERYFRAME_PAGE
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ItemFrame> {

        val pageGallery = params.key ?: 1
         return kotlin.runCatching {
             repository.getGalleryIdFrame(id, pageGallery).items
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
        private const val GALLERYFRAME_PAGE = 1
    }
}
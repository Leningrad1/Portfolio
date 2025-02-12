package com.example.cinemapark.presentation.recyclerviewadapter.top

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.cinemapark.data.retrofit.FilmRepository
import com.example.cinemapark.dataclass.eTop.Item

class FilmsTopPagingSource : PagingSource<Int, Item>() {
    private val repository = FilmRepository()
    var pageCount = 1

    override fun getRefreshKey(state: PagingState<Int, Item>): Int? = FIRST_PAGE

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Item> {
        val page = params.key ?: 1
        return kotlin.runCatching {
            repository.getFilmTopInfo(page).items
        }.fold(
            onSuccess = {
                LoadResult.Page(
                    data = it,
                    prevKey = null,
                    nextKey = if (page < pageCount) page + 1 else null
                )
            },
            onFailure = { LoadResult.Error(it) }
        )
    }


    companion object {
        private val FIRST_PAGE = 1
    }
}
package com.example.cinemapark.presentation.recyclerviewadapter.serias

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.cinemapark.data.retrofit.FilmRepository
import com.example.cinemapark.dataclass.serias.Item

class SeriasPagingSource : PagingSource<Int, Item>() {
    private val repository = FilmRepository()
    var pageCount = 1

    override fun getRefreshKey(state: PagingState<Int, Item>): Int? = SERIAS_PAGE

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Item> {
        val page = params.key ?: 1
        return kotlin.runCatching {
            repository.getSeriasInfo(page).items
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
        private val SERIAS_PAGE = 1
    }
}
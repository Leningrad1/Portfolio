package com.example.cinemapark.presentation.recyclerviewadapter.drame

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.cinemapark.data.retrofit.FilmRepository
import com.example.cinemapark.dataclass.drame.Item

class FilmDrameFrancePagingSource : PagingSource<Int, Item>() {
    private val repository = FilmRepository()
    var pageCount = 1

    override fun getRefreshKey(state: PagingState<Int, Item>): Int? = DRAME_PAGE

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Item> {
        val page = params.key ?: 1
        try {
            if (page > pageCount) {
                return LoadResult.Page(emptyList(), prevKey = null, nextKey = null)
            }

            val response = repository.getFilmDrameFranceInfo(page).items

            return LoadResult.Page(
                data = response,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (page < pageCount) page + 1 else null
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
    companion object {
        private val DRAME_PAGE = 1
    }
}
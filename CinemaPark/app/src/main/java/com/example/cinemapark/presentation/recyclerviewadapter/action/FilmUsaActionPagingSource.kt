package com.example.cinemapark.presentation.recyclerviewadapter.action

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.cinemapark.data.retrofit.FilmRepository
import com.example.cinemapark.dataclass.action.Item

class FilmUsaActionPagingSource : PagingSource<Int, Item>() {
    private val repository = FilmRepository()
    var pageCount = 1

    override fun getRefreshKey(state: PagingState<Int, Item>): Int? = USA_PAGE

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Item> {
        val page = params.key ?: 1
        try {
            if (page > pageCount) {
                return LoadResult.Page(emptyList(), prevKey = null, nextKey = null)
            }

            val response = repository.getFilmUsaMilitantsInfo(page).items

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
        private val USA_PAGE = 1
    }
}
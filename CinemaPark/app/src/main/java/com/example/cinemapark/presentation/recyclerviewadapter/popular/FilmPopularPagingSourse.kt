package com.example.cinemapark.presentation.recyclerviewadapter.popular

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.cinemapark.data.retrofit.FilmRepository
import com.example.cinemapark.dataclass.popular.ItemPopular

class FilmPopularPagingSourse : PagingSource<Int, ItemPopular>() {
    private val repository = FilmRepository()
    private val pageCount = 2
    override fun getRefreshKey(state: PagingState<Int, ItemPopular>): Int? = POPULAR_PAGE
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ItemPopular> {
        val pagePopular = params.key ?: 1
        try {
            if (pagePopular > pageCount) {
                return LoadResult.Page(emptyList(), prevKey = null, nextKey = null)
            }

            val response = repository.getFilmPopularInfo(pagePopular).items

            return LoadResult.Page(
                data = response,
                prevKey = if (pagePopular == 1) null else pagePopular - 1,
                nextKey = if (pagePopular < pageCount) pagePopular + 1 else null
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }

        /* return kotlin.runCatching {
             repository.getFilmPremierInfo(year, month).items
         }.fold(
             onSuccess = {
                 LoadResult.Page(
                     data = it,
                     prevKey = null,
                     nextKey = if (it.isEmpty()) null else pagePremier + 1
                 )
             },
             onFailure = { LoadResult.Error(it) }
         )*/
    }

    companion object {
        private val POPULAR_PAGE = 1
    }
}
package com.example.cinemapark.presentation.recyclerviewadapter.searchfilmparam

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.cinemapark.data.retrofit.FilmRepository
import com.example.cinemapark.dataclass.searchparam.Item

class SearchFilmParamPagingSource(
    private val repository: FilmRepository,
    private val country: Int,
    private val genre: Int,
    private val order: String,
    private val type: String,
    private val ratingFrom: Int,
    private val ratingTo: Int,
    private val yearFrom: Int,
    private val yearTo: Int
) : PagingSource<Int, Item>() {


    override fun getRefreshKey(state: PagingState<Int, Item>): Int? = state.anchorPosition

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Item> {
        val page = params.key ?: 1
        Log.d("page", "$page")
        try {
            return kotlin.runCatching {
                repository.getSearchParam(
                    country,
                    genre,
                    order,
                    type,
                    ratingFrom,
                    ratingTo,
                    yearFrom,
                    yearTo,
                    page
                ).items
            }.fold(
                onSuccess = {
                    LoadResult.Page(
                        data = it,
                        prevKey = null,
                        nextKey = if (it.isEmpty()) null else page + 1
                    )
                },
                onFailure = { LoadResult.Error(it) }
            )
            Log.d("repository.getSearchParamSource", "${repository.getSearchParam(
                country,
                genre,
                order,
                type,
                ratingFrom,
                ratingTo,
                yearFrom,
                yearTo,
                page
            ).items}")
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}
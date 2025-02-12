package com.example.cinemapark.presentation.recyclerviewadapter.searchkeyword

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.cinemapark.data.retrofit.FilmRepository
import com.example.cinemapark.dataclass.searchkeyword.Film

class SearchKeyWordPagingSource(
    private val repository: FilmRepository,
    private val valueSearchKeyWord: String
) : PagingSource<Int, Film>() {


    override fun getRefreshKey(state: PagingState<Int, Film>): Int? = state.anchorPosition

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Film> {
        val page = params.key ?: 1
        val keyWord = valueSearchKeyWord
        try {
            return kotlin.runCatching {
                Log.d("editTextSourse", "$keyWord")
                repository.getSearchKeyWord(keyWord, page).films
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
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

}
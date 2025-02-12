package com.example.cinemapark.presentation.recyclerviewadapter.premier

import android.annotation.SuppressLint
import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.cinemapark.data.retrofit.FilmRepository
import com.example.cinemapark.dataclass.premier.ItemPremier
import java.text.SimpleDateFormat
import java.util.Calendar

class FilmPremierPagingSourse: PagingSource<Int, ItemPremier>() {
    private val repository = FilmRepository()
    private val calendar: Calendar = Calendar.getInstance()
    var year = calendar.get(Calendar.YEAR)
    private var monthInt = calendar.get(Calendar.MONTH) + 1
    @SuppressLint("SimpleDateFormat")
    private val month = SimpleDateFormat("MMMM").format(calendar.time).uppercase()
    override fun getRefreshKey(state: PagingState<Int, ItemPremier>): Int? = PREMIER_PAGE
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ItemPremier> {
        val pagePremier = params.key ?: 1
        Log.d("year", "${year}, ${month}, ${monthInt}")
        return kotlin.runCatching {
            repository.getFilmPremierInfo(year, "MARCH").items  //не знаю почему, но у меня до марта выводит премьеры, month эта переменная которая должна быть указана вместо "MARCH"
        }.fold(
            onSuccess = {
                LoadResult.Page(
                    data = it,
                    prevKey = null,
                    nextKey = if (it.isEmpty()) null else pagePremier + 1
                )
            },
            onFailure = { LoadResult.Error(it) }
        )
    }
    companion object{
        private const val PREMIER_PAGE = 1
    }




}
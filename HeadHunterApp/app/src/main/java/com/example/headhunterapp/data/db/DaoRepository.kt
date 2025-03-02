package com.example.headhunterapp.data.db

import android.util.Log
import com.example.headhunterapp.Application
import com.example.headhunterapp.dataclass.dbdataclass.LikeVacancy
import kotlinx.coroutines.flow.Flow

class DaoRepository {
    fun getAllVacancyLike(): Flow<List<LikeVacancy>> {
        return Application.INSTANCE.db.userDao().getAllVacancy()
    }

    suspend fun insertLikeVacancy(likeVacancy: LikeVacancy) {
        Log.d("DaoRepository", "Inserting collectFilm: $likeVacancy")
        Application.INSTANCE.db.userDao().insert(likeVacancy)
    }
    suspend fun deleteLikeVacancy(likeVacancy: LikeVacancy) {
        return Application.INSTANCE.db.userDao().deleteMovieId(likeVacancy)
    }
}
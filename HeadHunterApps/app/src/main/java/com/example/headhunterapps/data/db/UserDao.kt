package com.example.headhunterapp.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.headhunterapp.model.dbdata.LikeVacancy
import kotlinx.coroutines.flow.Flow


@Dao
interface UserDao {
    @Transaction
    @Query("SELECT * FROM user_vacancy")
    fun getAllVacancy(): Flow<List<LikeVacancy>>

    @Insert(
        onConflict = OnConflictStrategy.REPLACE,
        entity = LikeVacancy::class
    )
    suspend fun insert(likeFilm: LikeVacancy)

    @Delete
    suspend fun deleteMovieId(movie: LikeVacancy)
}
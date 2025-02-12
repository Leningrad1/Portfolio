package com.example.cinemapark

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cinemapark.data.db.UserDao
import com.example.cinemapark.dataclass.dbcollectfilm.CollectFilm
import com.example.cinemapark.dataclass.dbcollectfilm.Movie
import com.example.cinemapark.dataclass.dbcollectfilm.MovieListMovie
import com.example.cinemapark.dataclass.dbcollectfilm.UserMovie


@Database(
    entities = [
        UserMovie::class,
        CollectFilm::class,
        Movie::class,
        MovieListMovie::class],
    version = 1,
    exportSchema = true
)
abstract class AppDataBase : RoomDatabase() {

    abstract fun userDao(): UserDao

}
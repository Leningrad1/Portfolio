package com.example.headhunterapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.headhunterapp.model.dbdata.LikeVacancy

@Database(
    entities = [
        LikeVacancy::class],
    version = 1,
    exportSchema = false
)
abstract class AppDataBase : RoomDatabase() {

    abstract fun userDao(): UserDao

}
package com.example.headhunterapp

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.headhunterapp.data.db.UserDao
import com.example.headhunterapp.dataclass.dbdataclass.LikeVacancy

@Database(
    entities = [
        LikeVacancy::class],
    version = 1,
    exportSchema = true
)
abstract class AppDataBase : RoomDatabase() {

    abstract fun userDao(): UserDao

}
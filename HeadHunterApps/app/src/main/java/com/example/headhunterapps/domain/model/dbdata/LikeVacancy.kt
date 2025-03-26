package com.example.headhunterapp.model.dbdata

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_vacancy")
data class LikeVacancy(
    @PrimaryKey
    @ColumnInfo(name = "id_vacancy")
    val id: String,
    @ColumnInfo(name = "like_vacancy")
    var isFavorite: Boolean,

)

package com.example.headhunterapp.data.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import com.example.headhunterapp.model.dbdata.LikeVacancy;
import kotlinx.coroutines.flow.Flow;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0003\bg\u0018\u00002\u00020\u0001J\u0019\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0006J\u0014\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\t0\bH\'J\u0019\u0010\n\u001a\u00020\u00032\u0006\u0010\u000b\u001a\u00020\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0006\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\f"}, d2 = {"Lcom/example/headhunterapp/data/db/UserDao;", "", "deleteMovieId", "", "movie", "Lcom/example/headhunterapp/model/dbdata/LikeVacancy;", "(Lcom/example/headhunterapp/model/dbdata/LikeVacancy;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllVacancy", "Lkotlinx/coroutines/flow/Flow;", "", "insert", "likeFilm", "app_debug"})
@androidx.room.Dao
public abstract interface UserDao {
    
    @androidx.room.Transaction
    @androidx.room.Query(value = "SELECT * FROM user_vacancy")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.example.headhunterapp.model.dbdata.LikeVacancy>> getAllVacancy();
    
    @androidx.room.Insert(onConflict = 1, entity = com.example.headhunterapp.model.dbdata.LikeVacancy.class)
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object insert(@org.jetbrains.annotations.NotNull
    com.example.headhunterapp.model.dbdata.LikeVacancy likeFilm, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Delete
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object deleteMovieId(@org.jetbrains.annotations.NotNull
    com.example.headhunterapp.model.dbdata.LikeVacancy movie, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
}
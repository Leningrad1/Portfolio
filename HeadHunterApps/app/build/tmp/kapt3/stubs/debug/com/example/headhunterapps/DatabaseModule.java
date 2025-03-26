package com.example.headhunterapps;

import android.app.Application;
import android.content.Context;
import androidx.room.Room;
import com.example.headhunterapp.data.db.AppDataBase;
import com.example.headhunterapp.data.db.UserDao;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.HiltAndroidApp;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import javax.inject.Singleton;

@dagger.Module
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007\u00a8\u0006\u0007"}, d2 = {"Lcom/example/headhunterapps/DatabaseModule;", "", "()V", "provideUserDao", "Lcom/example/headhunterapp/data/db/UserDao;", "db", "Lcom/example/headhunterapp/data/db/AppDataBase;", "app_debug"})
@dagger.hilt.InstallIn(value = {dagger.hilt.components.SingletonComponent.class})
public final class DatabaseModule {
    @org.jetbrains.annotations.NotNull
    public static final com.example.headhunterapps.DatabaseModule INSTANCE = null;
    
    private DatabaseModule() {
        super();
    }
    
    @dagger.Provides
    @org.jetbrains.annotations.NotNull
    public final com.example.headhunterapp.data.db.UserDao provideUserDao(@org.jetbrains.annotations.NotNull
    com.example.headhunterapp.data.db.AppDataBase db) {
        return null;
    }
}
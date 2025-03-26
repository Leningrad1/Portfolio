package com.example.headhunterapp.data.repository;

import com.example.headhunterapp.data.db.UserDao;
import com.example.headhunterapp.model.Address;
import com.example.headhunterapp.model.Button;
import com.example.headhunterapp.model.Experience;
import com.example.headhunterapp.model.Offer;
import com.example.headhunterapp.model.Salary;
import com.example.headhunterapp.model.Vacancy;
import com.example.headhunterapps.R;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import javax.inject.Inject;
import javax.inject.Singleton;

@dagger.Module
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007\u00a8\u0006\u0007"}, d2 = {"Lcom/example/headhunterapp/data/repository/RepositoryModule;", "", "()V", "provideRepository", "Lcom/example/headhunterapp/data/repository/Repository;", "userDao", "Lcom/example/headhunterapp/data/db/UserDao;", "app_debug"})
@dagger.hilt.InstallIn(value = {dagger.hilt.components.SingletonComponent.class})
public final class RepositoryModule {
    @org.jetbrains.annotations.NotNull
    public static final com.example.headhunterapp.data.repository.RepositoryModule INSTANCE = null;
    
    private RepositoryModule() {
        super();
    }
    
    @dagger.Provides
    @javax.inject.Singleton
    @org.jetbrains.annotations.NotNull
    public final com.example.headhunterapp.data.repository.Repository provideRepository(@org.jetbrains.annotations.NotNull
    com.example.headhunterapp.data.db.UserDao userDao) {
        return null;
    }
}
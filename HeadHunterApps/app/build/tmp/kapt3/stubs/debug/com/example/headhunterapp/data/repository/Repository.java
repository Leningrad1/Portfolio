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

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R \u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\t\"\u0004\b\r\u0010\u000e\u00a8\u0006\u000f"}, d2 = {"Lcom/example/headhunterapp/data/repository/Repository;", "", "userDao", "Lcom/example/headhunterapp/data/db/UserDao;", "(Lcom/example/headhunterapp/data/db/UserDao;)V", "offersList", "", "Lcom/example/headhunterapp/model/Offer;", "getOffersList", "()Ljava/util/List;", "vacanciesListAll", "Lcom/example/headhunterapp/model/Vacancy;", "getVacanciesListAll", "setVacanciesListAll", "(Ljava/util/List;)V", "app_debug"})
public final class Repository {
    @org.jetbrains.annotations.NotNull
    private final com.example.headhunterapp.data.db.UserDao userDao = null;
    @org.jetbrains.annotations.NotNull
    private final java.util.List<com.example.headhunterapp.model.Offer> offersList = null;
    @org.jetbrains.annotations.NotNull
    private java.util.List<com.example.headhunterapp.model.Vacancy> vacanciesListAll;
    
    @javax.inject.Inject
    public Repository(@org.jetbrains.annotations.NotNull
    com.example.headhunterapp.data.db.UserDao userDao) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.example.headhunterapp.model.Offer> getOffersList() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.example.headhunterapp.model.Vacancy> getVacanciesListAll() {
        return null;
    }
    
    public final void setVacanciesListAll(@org.jetbrains.annotations.NotNull
    java.util.List<com.example.headhunterapp.model.Vacancy> p0) {
    }
}